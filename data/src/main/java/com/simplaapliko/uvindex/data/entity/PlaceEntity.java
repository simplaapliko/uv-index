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

package com.simplaapliko.uvindex.data.entity;

public class PlaceEntity extends Entity {

    private final Integer order;
    private final String name;
    private final String country;
    private final Double latitude;
    private final Double longitude;
    private final String timeZoneId;

    public PlaceEntity(Long id, Integer order, String name, String country, Double latitude,
            Double longitude, String timeZoneId) {
        this.id = id;
        this.order = order;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeZoneId = timeZoneId;
    }

    public Integer getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PlaceEntity that = (PlaceEntity) o;

        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) {
            return false;
        }
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) {
            return false;
        }
        return timeZoneId != null ? timeZoneId.equals(that.timeZoneId) : that.timeZoneId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0);
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0);
        result = 31 * result + (timeZoneId != null ? timeZoneId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PlaceEntity{" + "order=" + order + ", name='" + name + '\'' + ", country='"
                + country + '\'' + ", latitude=" + latitude + ", longitude=" + longitude
                + ", timeZoneId='" + timeZoneId + '\'' + "} " + super.toString();
    }

    public static class Builder {
        private Long id;
        private Integer order;
        private String name;
        private String country;
        private Double latitude;
        private Double longitude;
        private String timeZoneId;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setOrder(Integer order) {
            this.order = order;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setLatitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
            return this;
        }

        public PlaceEntity build() {
            return new PlaceEntity(id, order, name, country, latitude, longitude, timeZoneId);
        }
    }
}
