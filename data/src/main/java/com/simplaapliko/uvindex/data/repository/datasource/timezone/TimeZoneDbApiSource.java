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
import com.simplaapliko.uvindex.data.exception.InvalidResponseException;
import com.simplaapliko.uvindex.data.exception.RemoteServerConnectionException;
import com.simplaapliko.uvindex.data.rest.tzdb.TimeZoneDbRestApi;
import com.simplaapliko.uvindex.data.rest.tzdb.TimeZoneResponse;
import com.simplaapliko.uvindex.data.rest.tzdb.TimeZoneResponseMapper;

import java.util.TimeZone;

import io.reactivex.Maybe;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

public class TimeZoneDbApiSource implements TimeZoneDataSource {

    private final TimeZoneDbRestApi mTimeZoneDbService;
    private final TimeZoneResponseMapper mTimeZoneResponseMapper;

    private static ObservableTransformer<Result<TimeZoneResponse>, Result<TimeZoneResponse>> applyResponseValidation() {
        return observable -> observable.map(checkRemoteServerConnection())
                .map(checkHttpExceptions())
                .map(checkStatusCode());
    }

    private static Function<Result<TimeZoneResponse>, Result<TimeZoneResponse>> checkHttpExceptions() {
        return result -> {
            Response<TimeZoneResponse> response = result.response();
            // assume that value has been already checked and it is not null at this point
            //noinspection ConstantConditions
            if (!response.isSuccessful()) {
                throw new HttpException("TimeZoneDb api response error: " + response.code());
            }
            return result;
        };
    }

    private static Function<Result<TimeZoneResponse>, Result<TimeZoneResponse>> checkRemoteServerConnection() {
        return result -> {
            if (result.isError()) {
                throw new RemoteServerConnectionException("TimeZoneDb api result error");
            }
            return result;
        };
    }

    private static Function<Result<TimeZoneResponse>, Result<TimeZoneResponse>> checkStatusCode() {
        return result -> {
            // assume that value has been already checked and it is not null at this point
            //noinspection ConstantConditions
            TimeZoneResponse apiResponse = result.response()
                    .body();

            // assume that TimeZoneDb api always returns status code
            //noinspection ConstantConditions
            switch (apiResponse.getStatus()) {
                case TimeZoneResponse.STATUS_OK:
                    break;
                case TimeZoneResponse.STATUS_FAILED:
                    throw new InvalidResponseException(apiResponse.getMessage());
                default:
                    throw new InvalidResponseException("Unknown response status");
            }
            return result;
        };
    }

    public TimeZoneDbApiSource(TimeZoneDbRestApi timeZoneDbService,
            TimeZoneResponseMapper timeZoneResponseMapper) {
        mTimeZoneDbService = timeZoneDbService;
        mTimeZoneResponseMapper = timeZoneResponseMapper;
    }

    @Override
    public Maybe<TimeZone> timeZone(double latitude, double longitude, int timeStamp) {
        return mTimeZoneDbService.timeZone(latitude, longitude, timeStamp)
                .compose(applyResponseValidation())
                .map(result -> result.response()
                        .body())
                .map(mTimeZoneResponseMapper::toTimeZone)
                .singleElement();
    }
}
