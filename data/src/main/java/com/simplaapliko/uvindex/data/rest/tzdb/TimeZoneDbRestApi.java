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

package com.simplaapliko.uvindex.data.rest.tzdb;

import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TimeZoneDbRestApi {

    String BASE_URL = "http://api.timezonedb.com/v2/";

    @GET("get-time-zone?by=position&fields=zoneName")
    Observable<Result<TimeZoneResponse>> timeZone(@Query("lat") double latitude,
            @Query("lng") double longitude, @Query("time") int timestamp);
}
