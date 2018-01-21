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

package com.simplaapliko.uvindex.data.rest.google;

import android.support.annotation.Nullable;

import java.util.TimeZone;

public class TimeZoneResponseMapper {

    /**
     * Maps {@link TimeZoneResponse} to {@link TimeZone}.
     *
     * @param response {@link TimeZoneResponse} to be mapped to {@link TimeZone}
     * @return the mapped {@link TimeZone} or null
     */
    @Nullable
    public TimeZone toTimeZone(TimeZoneResponse response) {
        TimeZone timeZone = null;
        if (response != null) {
            timeZone = TimeZone.getTimeZone(response.getTimeZoneId());
        }
        return timeZone;
    }
}
