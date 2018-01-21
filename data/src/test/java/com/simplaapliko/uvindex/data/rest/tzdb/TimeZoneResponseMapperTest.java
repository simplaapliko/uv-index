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

import org.junit.Before;
import org.junit.Test;

import java.util.TimeZone;

import static com.google.common.truth.Truth.assertThat;

public class TimeZoneResponseMapperTest {

    private TimeZoneResponseMapper mTimeZoneResponseMapper;

    @Before
    public void setUp() {
        mTimeZoneResponseMapper = new TimeZoneResponseMapper();
    }

    @Test
    public void toModel() {
        TimeZoneResponse timeZoneResponse = new TimeZoneResponse();
        timeZoneResponse.setZoneName(TimeZone.getDefault()
                .getID());

        TimeZone actualResult = mTimeZoneResponseMapper.toTimeZone(timeZoneResponse);
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getID()).isEqualTo(timeZoneResponse.getZoneName());
    }

    @Test
    public void toModel_null() {
        TimeZone actualResult = mTimeZoneResponseMapper.toTimeZone(null);
        assertThat(actualResult).isNull();
    }
}
