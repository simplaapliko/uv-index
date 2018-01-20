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

package com.simplaapliko.uvindex.data.sqlite.table;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.simplaapliko.uvindex.data.entity.PlaceEntity;
import com.simplaapliko.uvindex.data.sqlite.CursorMapper;
import com.simplaapliko.uvindex.data.sqlite.DataSet;

import io.reactivex.functions.Function;

public class PlaceTable implements DataSet {

    public static final String TABLE = "place";

    public static String ALIAS_PLACE = TABLE;
    public static final String PLACE_ID = ALIAS_PLACE + "." + Columns._ID;
    public static final String PLACE_ORDER = ALIAS_PLACE + "." + Columns.ORDER;
    public static final String PLACE_NAME = ALIAS_PLACE + "." + Columns.NAME;
    public static final String PLACE_COUNTRY = ALIAS_PLACE + "." + Columns.COUNTRY;
    public static final String PLACE_LATITUDE = ALIAS_PLACE + "." + Columns.LATITUDE;
    public static final String PLACE_LONGITUDE = ALIAS_PLACE + "." + Columns.LONGITUDE;
    public static final String PLACE_TIMEZONE_ID = ALIAS_PLACE + "." + Columns.TIMEZONE_ID;

    public static final String QUERY =
            "" + "SELECT " + PLACE_ID + ", " + PLACE_ORDER + ", " + PLACE_NAME + ", "
                    + PLACE_COUNTRY + ", " + PLACE_LATITUDE + ", " + PLACE_LONGITUDE + ", "
                    + PLACE_TIMEZONE_ID + " FROM " + TABLE + " AS " + ALIAS_PLACE;

    private static final String CREATE_TABLE =
            "" + "CREATE TABLE IF NOT EXISTS " + TABLE + " (" + Columns._ID
                    + " INTEGER PRIMARY KEY, " + Columns.ORDER + " INTEGER NOT NULL, "
                    + Columns.NAME + " TEXT NOT NULL, " + Columns.COUNTRY + " TEXT NOT NULL, "
                    + Columns.LATITUDE + " INTEGER NOT NULL, " + Columns.LONGITUDE
                    + " INTEGER NOT NULL, " + Columns.TIMEZONE_ID + " TEXT NOT NULL);";

    public static Function<Cursor, PlaceEntity> MAPPER = cursor -> {
        return new PlaceEntity.Builder().setId(CursorMapper.getLong(cursor, Columns._ID))
                .setOrder(CursorMapper.getInt(cursor, Columns.ORDER))
                .setName(CursorMapper.getString(cursor, Columns.NAME))
                .setCountry(CursorMapper.getString(cursor, Columns.COUNTRY))
                .setLatitude(CursorMapper.getDouble(cursor, Columns.LATITUDE))
                .setLongitude(CursorMapper.getDouble(cursor, Columns.LONGITUDE))
                .setTimeZoneId(CursorMapper.getString(cursor, Columns.TIMEZONE_ID))
                .build();
    };

    public PlaceTable() {
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // currently nothing to update
    }

    public static class Columns implements BaseColumns {
        public static final String ORDER = "sort_order";
        public static final String NAME = "name";
        public static final String COUNTRY = "country";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String TIMEZONE_ID = "timezone_id";
    }
}
