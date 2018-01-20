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

package com.simplaapliko.uvindex.domain.model;

public class Place {

    public static final long NEW_ID = -1;

    private final long id;
    private final int order;
    private final String name;
    private final String country;
    private final double latitude;
    private final double longitude;
    private final String timeZoneId;

    private Place(long id, int order, String name, String country, double latitude,
            double longitude, String timeZoneId) {
        this.id = id;
        this.order = order;
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeZoneId = timeZoneId;
    }

    public boolean isNew() {
        return id == NEW_ID;
    }

    public long getId() {
        return id;
    }

    public int getOrder() {
        return order;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getDescription() {
        if (country != null && !country.isEmpty()) {
            return name + ", " + country;
        } else {
            return name;
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
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

        Place place = (Place) o;

        if (id != place.id) return false;
        if (order != place.order) return false;
        if (Double.compare(place.latitude, latitude) != 0) return false;
        if (Double.compare(place.longitude, longitude) != 0) return false;
        if (name != null ? !name.equals(place.name) : place.name != null) return false;
        if (country != null ? !country.equals(place.country) : place.country != null) return false;
        return timeZoneId != null ? timeZoneId.equals(place.timeZoneId) : place.timeZoneId == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + order;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (timeZoneId != null ? timeZoneId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Place{" + "id=" + id + ", order=" + order + ", name='" + name + '\'' + ", country='"
                + country + '\'' + ", latitude=" + latitude + ", longitude=" + longitude
                + ", timeZoneId='" + timeZoneId + '\'' + '}';
    }

    public static class Builder {
        private long id;
        private int order;
        private String name;
        private String country;
        private double latitude;
        private double longitude;
        private String timeZoneId;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setOrder(int order) {
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

        public Builder setLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder setLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder setTimeZoneId(String timeZoneId) {
            this.timeZoneId = timeZoneId;
            return this;
        }

        public Place build() {
            return new Place(id, order, name, country, latitude, longitude, timeZoneId);
        }
    }
}
