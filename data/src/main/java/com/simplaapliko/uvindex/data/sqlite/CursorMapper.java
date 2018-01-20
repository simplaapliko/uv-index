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

package com.simplaapliko.uvindex.data.sqlite;

import android.database.Cursor;

public class CursorMapper {

    public static final int BOOLEAN_FALSE = 0;
    public static final int BOOLEAN_TRUE = 1;

    private CursorMapper() {
        throw new AssertionError("Must not instantiate an element of this class");
    }

    public static String getString(Cursor cursor, String columnName) {
        return getString(cursor, cursor.getColumnIndexOrThrow(columnName));
    }

    public static String getString(Cursor cursor, int columnIndex) {
        return cursor.getString(columnIndex);
    }

    public static boolean getBoolean(Cursor cursor, String columnName) {
        return getInt(cursor, columnName) == BOOLEAN_TRUE;
    }

    public static boolean getBoolean(Cursor cursor, int columnIndex) {
        return getInt(cursor, columnIndex) == BOOLEAN_TRUE;
    }

    public static double getDouble(Cursor cursor, String columnName) {
        return getDouble(cursor, cursor.getColumnIndexOrThrow(columnName));
    }

    public static double getDouble(Cursor cursor, int columnIndex) {
        return cursor.getDouble(columnIndex);
    }

    public static int getInt(Cursor cursor, String columnName) {
        return getInt(cursor, cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getInt(Cursor cursor, int columnIndex) {
        return cursor.getInt(columnIndex);
    }

    public static Integer getInteger(Cursor cursor, int columnIndex) {
        Integer result = null;
        if (!cursor.isNull(columnIndex)) {
            result = cursor.getInt(columnIndex);
        }
        return result;
    }

    public static long getLong(Cursor cursor, String columnName) {
        return getLong(cursor, cursor.getColumnIndexOrThrow(columnName));
    }

    public static long getLong(Cursor cursor, int columnIndex) {
        return cursor.getLong(columnIndex);
    }
}
