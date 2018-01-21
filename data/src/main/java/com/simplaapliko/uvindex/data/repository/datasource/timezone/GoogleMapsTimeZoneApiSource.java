/*
 * Copyright (C) 2018 Oleg Kan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simplaapliko.uvindex.data.repository.datasource.timezone;

import com.simplaapliko.uvindex.data.exception.HttpException;
import com.simplaapliko.uvindex.data.exception.InvalidRequestException;
import com.simplaapliko.uvindex.data.exception.InvalidResponseException;
import com.simplaapliko.uvindex.data.exception.OverQueryLimitException;
import com.simplaapliko.uvindex.data.exception.RemoteServerConnectionException;
import com.simplaapliko.uvindex.data.exception.RequestDeniedException;
import com.simplaapliko.uvindex.data.exception.ZeroResultsException;
import com.simplaapliko.uvindex.data.rest.google.GoogleApiResponse;
import com.simplaapliko.uvindex.data.rest.google.GoogleMapsRestApi;
import com.simplaapliko.uvindex.data.rest.google.TimeZoneResponseMapper;
import com.simplaapliko.uvindex.data.util.StringUtils;

import java.util.TimeZone;

import io.reactivex.Maybe;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

public class GoogleMapsTimeZoneApiSource implements TimeZoneDataSource {

    private final GoogleMapsRestApi mGoogleMapsService;
    private final TimeZoneResponseMapper mTimeZoneResponseMapper;

    private static <T extends GoogleApiResponse> ObservableTransformer<Result<T>, Result<T>> applyResponseValidation() {
        return observable -> observable.map(checkRemoteServerConnection())
                .map(checkHttpExceptions())
                .map(checkStatusCode());
    }

    private static <T extends GoogleApiResponse> Function<Result<T>, Result<T>> checkHttpExceptions() {
        return result -> {
            Response<T> response = result.response();
            // assume that value has been already checked and it is not null at this point
            //noinspection ConstantConditions
            if (!response.isSuccessful()) {
                throw new HttpException("Google api response error: " + response.code());
            }
            return result;
        };
    }

    private static <T extends GoogleApiResponse> Function<Result<T>, Result<T>> checkRemoteServerConnection() {
        return result -> {
            if (result.isError()) {
                throw new RemoteServerConnectionException("Google api result error");
            }
            return result;
        };
    }

    private static <T extends GoogleApiResponse> Function<Result<T>, Result<T>> checkStatusCode() {
        return result -> {
            // assume that value has been already checked and it is not null at this point
            //noinspection ConstantConditions
            T apiResponse = result.response()
                    .body();

            // assume that google api always returns status code
            //noinspection ConstantConditions
            switch (apiResponse.getStatus()) {
                case GoogleApiResponse.STATUS_OK:
                    break;
                case GoogleApiResponse.STATUS_INVALID_REQUEST:
                    throw new InvalidRequestException("Invalid request");
                case GoogleApiResponse.STATUS_OVER_QUERY_LIMIT:
                    throw new OverQueryLimitException("Over query limit");
                case GoogleApiResponse.STATUS_REQUEST_DENIED:
                    throw new RequestDeniedException("Request denied");
                case GoogleApiResponse.STATUS_ZERO_RESULTS:
                    throw new ZeroResultsException("Zero results");
                default:
                    throw new InvalidResponseException("Unknown response status");
            }
            return result;
        };
    }

    public GoogleMapsTimeZoneApiSource(GoogleMapsRestApi googleMapsService,
            TimeZoneResponseMapper timeZoneResponseMapper) {
        mGoogleMapsService = googleMapsService;
        mTimeZoneResponseMapper = timeZoneResponseMapper;
    }

    @Override
    public Maybe<TimeZone> timeZone(double latitude, double longitude, int timeStamp) {
        return Maybe.defer(() -> {
            String lat = StringUtils.formatCoordinate(latitude);
            String lng = StringUtils.formatCoordinate(longitude);
            String coordinates = lat + "," + lng;

            return mGoogleMapsService.timeZone(coordinates, timeStamp)
                    .compose(applyResponseValidation())
                    .map(result -> result.response()
                            .body())
                    .map(mTimeZoneResponseMapper::toTimeZone)
                    .singleElement();
        });
    }
}
