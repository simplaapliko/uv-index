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
import com.simplaapliko.uvindex.data.rest.google.GoogleMapsRestApi;
import com.simplaapliko.uvindex.data.rest.google.TimeZoneResponse;
import com.simplaapliko.uvindex.data.rest.google.TimeZoneResponseMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.TimeZone;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava2.Result;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GoogleMapsTimeZoneApiSourceTest {

    private static final String COORDINATES = "42.3416847,69.5901009";
    private static final double LAT = 42.3416847;
    private static final double LNG = 69.5901009;
    private static final int TIME_STAMP = (int) (System.currentTimeMillis() / 1000);

    @Mock GoogleMapsRestApi mockRestApi;
    @Mock TimeZoneResponseMapper mockMapper;
    private GoogleMapsTimeZoneApiSource mTimeZoneApiSource;

    public static Result<TimeZoneResponse> fakeResult(String status) {
        TimeZoneResponse timeZoneResponse = new TimeZoneResponse();
        timeZoneResponse.setStatus(status);

        Response<TimeZoneResponse> response = Response.success(timeZoneResponse);

        return Result.response(response);
    }

    public static Result<TimeZoneResponse> fakeResult(int code) {
        Response<TimeZoneResponse> response =
                Response.error(code, ResponseBody.create(MediaType.parse(""), new byte[0]));
        return Result.response(response);
    }

    @Before
    public void setUp() {
        mTimeZoneApiSource = new GoogleMapsTimeZoneApiSource(mockRestApi, mockMapper);
    }

    @Test
    public void timeZone_statusOk() {
        Result<TimeZoneResponse> result = fakeResult(TimeZoneResponse.STATUS_OK);
        when(mockRestApi.timeZone(COORDINATES, TIME_STAMP)).thenReturn(Observable.just(result));

        TimeZone timeZone = TimeZone.getDefault();
        when(mockMapper.toTimeZone(result.response()
                .body())).thenReturn(timeZone);

        TestObserver<TimeZone> testObserver = new TestObserver<>();
        mTimeZoneApiSource.timeZone(LAT, LNG, TIME_STAMP)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        testObserver.assertValue(timeZone);
    }

    @Test
    public void timeZone_statusInvalidRequest() {
        Result<TimeZoneResponse> result = fakeResult(TimeZoneResponse.STATUS_INVALID_REQUEST);
        when(mockRestApi.timeZone(COORDINATES, TIME_STAMP)).thenReturn(Observable.just(result));

        TestObserver<TimeZone> testObserver = new TestObserver<>();
        mTimeZoneApiSource.timeZone(LAT, LNG, TIME_STAMP)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertError(InvalidRequestException.class);
    }

    @Test
    public void timeZone_statusOverQueryLimit() {
        Result<TimeZoneResponse> result = fakeResult(TimeZoneResponse.STATUS_OVER_QUERY_LIMIT);
        when(mockRestApi.timeZone(COORDINATES, TIME_STAMP)).thenReturn(Observable.just(result));

        TestObserver<TimeZone> testObserver = new TestObserver<>();
        mTimeZoneApiSource.timeZone(LAT, LNG, TIME_STAMP)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertError(OverQueryLimitException.class);
    }

    @Test
    public void timeZone_statusRequestDenied() {
        Result<TimeZoneResponse> result = fakeResult(TimeZoneResponse.STATUS_REQUEST_DENIED);
        when(mockRestApi.timeZone(COORDINATES, TIME_STAMP)).thenReturn(Observable.just(result));

        TestObserver<TimeZone> testObserver = new TestObserver<>();
        mTimeZoneApiSource.timeZone(LAT, LNG, TIME_STAMP)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertError(RequestDeniedException.class);
    }

    @Test
    public void timeZone_statusZeroResults() {
        Result<TimeZoneResponse> result = fakeResult(TimeZoneResponse.STATUS_ZERO_RESULTS);
        when(mockRestApi.timeZone(COORDINATES, TIME_STAMP)).thenReturn(Observable.just(result));

        TestObserver<TimeZone> testObserver = new TestObserver<>();
        mTimeZoneApiSource.timeZone(LAT, LNG, TIME_STAMP)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertError(ZeroResultsException.class);
    }

    @Test
    public void timeZone_statusUnknown() {
        Result<TimeZoneResponse> result = fakeResult("Unknown status");
        when(mockRestApi.timeZone(COORDINATES, TIME_STAMP)).thenReturn(Observable.just(result));

        TestObserver<TimeZone> testObserver = new TestObserver<>();
        mTimeZoneApiSource.timeZone(LAT, LNG, TIME_STAMP)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertError(InvalidResponseException.class);
    }

    @Test
    public void timeZone_HttpException() {
        Result<TimeZoneResponse> result = fakeResult(404);
        when(mockRestApi.timeZone(COORDINATES, TIME_STAMP)).thenReturn(Observable.just(result));

        TestObserver<TimeZone> testObserver = new TestObserver<>();
        mTimeZoneApiSource.timeZone(LAT, LNG, TIME_STAMP)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertError(HttpException.class);
    }

    @Test
    public void timeZone_RemoteServerConnectionException() {
        Result<TimeZoneResponse> result = Result.error(new Exception());
        when(mockRestApi.timeZone(COORDINATES, TIME_STAMP)).thenReturn(Observable.just(result));

        TestObserver<TimeZone> testObserver = new TestObserver<>();
        mTimeZoneApiSource.timeZone(LAT, LNG, TIME_STAMP)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertError(RemoteServerConnectionException.class);
    }
}
