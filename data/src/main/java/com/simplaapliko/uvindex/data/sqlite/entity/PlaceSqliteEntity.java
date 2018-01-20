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

package com.simplaapliko.uvindex.data.sqlite.entity;

import android.content.ContentValues;
import android.support.annotation.Nullable;

import com.simplaapliko.uvindex.data.entity.PlaceEntity;
import com.simplaapliko.uvindex.data.sqlite.table.PlaceTable;

public class PlaceSqliteEntity implements SqliteEntity {

    private final PlaceEntity mPlaceEntity;

    public PlaceSqliteEntity(final PlaceEntity placeEntity) {
        mPlaceEntity = placeEntity;
    }

    public PlaceEntity getPlaceEntity() {
        return mPlaceEntity;
    }

    @Nullable
    @Override
    public ContentValues toContentValues() {
        ContentValues cv = null;
        if (mPlaceEntity != null) {
            cv = new ContentValues();
            cv.put(PlaceTable.Columns.ORDER, mPlaceEntity.getOrder());
            cv.put(PlaceTable.Columns.NAME, mPlaceEntity.getName());
            cv.put(PlaceTable.Columns.COUNTRY, mPlaceEntity.getCountry());
            cv.put(PlaceTable.Columns.LATITUDE, mPlaceEntity.getLatitude());
            cv.put(PlaceTable.Columns.LONGITUDE, mPlaceEntity.getLongitude());
            cv.put(PlaceTable.Columns.TIMEZONE_ID, mPlaceEntity.getTimeZoneId());
        }
        return cv;
    }

    @Override
    public boolean isNew() {
        return mPlaceEntity != null && mPlaceEntity.isNew();
    }

    @Override
    public Long getId() {
        if (mPlaceEntity == null) {
            return NEW_ID;
        }
        return mPlaceEntity.getId();
    }

    @Override
    public void setId(Long id) {
        if (mPlaceEntity != null) {
            mPlaceEntity.setId(id);
        }
    }
}
