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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public abstract class GoogleApiResponse {

    /** indicates that no errors occurred and at least one result was returned */
    public static final String STATUS_OK = "OK";

    /** generally indicates that some input parameter is missing */
    public static final String STATUS_INVALID_REQUEST = "INVALID_REQUEST";

    /** indicates that you are over your quota */
    public static final String STATUS_OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";

    /**
     * indicates that your request was denied,
     * generally because of lack of an invalid key parameter
     */
    public static final String STATUS_REQUEST_DENIED = "REQUEST_DENIED";

    /** indicates that no data could be found */
    public static final String STATUS_ZERO_RESULTS = "ZERO_RESULTS";

    @SerializedName("status") @Expose private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
