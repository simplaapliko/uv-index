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

package com.simplaapliko.uvindex.data.repository;

import com.simplaapliko.uvindex.data.repository.datasource.timezone.TimeZoneDataSource;
import com.simplaapliko.uvindex.domain.repository.TimeZoneRepository;

import java.util.TimeZone;

import io.reactivex.Maybe;

public class TimeZoneDataRepository implements TimeZoneRepository {

    private final TimeZoneDataSource mTimeZoneDataSource;

    public TimeZoneDataRepository(TimeZoneDataSource timeZoneDataSource) {
        mTimeZoneDataSource = timeZoneDataSource;
    }

    @Override
    public Maybe<TimeZone> timeZone(double latitude, double longitude) {
        return Maybe.defer(() -> {
            int timeStamp = (int) (System.currentTimeMillis() / 1000);

            return mTimeZoneDataSource.timeZone(latitude, longitude, timeStamp);
        });
    }
}
