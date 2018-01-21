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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;

import com.simplaapliko.uvindex.data.sqlite.entity.SqliteEntity;
import com.simplaapliko.uvindex.data.sqlite.table.PlaceTable;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    private static final String TAG = "Database";

    private static final String DATABASE_NAME = "golden_hour.db";
    private static final int DATABASE_VERSION = 4;

    private static List<DataSet> mDataSets = new ArrayList<>();

    public static Database getInstance(Context context) {
        return new Database(context);
    }

    private Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        if (mDataSets.size() == 0) {
            mDataSets.add(new PlaceTable());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for (DataSet dataSet : mDataSets) {
            dataSet.onCreate(db);
        }
        //LogManager.log(TAG, "database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            //LogManager.log(TAG, "database upgraded");
        }
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase db = super.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys = 1");
        return db;
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    public Cursor query(String sql, String[] selectionArgs) {
        return getReadableDatabase().rawQuery(sql, selectionArgs);
    }

    public long insertOrUpdate(String table, SqliteEntity entity) {
        long result;
        if (entity.isNew()) {
            result = insert(table, entity);
        } else {
            result = update(table, entity);
        }

        return result;
    }

    public long insert(String table, @Nullable SqliteEntity entity) {
        if (entity == null) {
            return SqliteEntity.NEW_ID;
        }
        long id = insert(table, entity.toContentValues());
        entity.setId(id);
        return id;
    }

    private long insert(String table, ContentValues cv) {
        // keep this method private, use insert(String, Entity),
        // because it sets id for the inserted entity
        long id;

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            id = db.insertOrThrow(table, null, cv);
            db.setTransactionSuccessful();
            //LogManager.log(TAG, "insert: " + table + ", id = " + id + ", cv = " + cv);
        } catch (SQLException e) {
            //LogManager.log(TAG, e.getClass() + " error: " + e.getMessage());
            throw e;
        } finally {
            db.endTransaction();
            db.close();
        }

        return id;
    }

    public int update(String table, @Nullable SqliteEntity entity) {
        if (entity == null) {
            return 0;
        }
        return update(table, entity.getId(), entity.toContentValues());
    }

    private int update(String table, long id, ContentValues cv) {
        return update(table, BaseColumns._ID + "=" + id, cv);
    }

    public int update(String table, String selection, ContentValues cv) {
        int rowsAffected;

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            rowsAffected = db.update(table, cv, selection, null);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            //LogManager.log(TAG, e.getClass() + " error: " + e.getMessage());
            throw e;
        } finally {
            db.endTransaction();
            db.close();
        }

        return rowsAffected;
    }

    public int delete(String table, long id) {
        return delete(table, BaseColumns._ID + "=?", new String[] { String.valueOf(id) });
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        int rowsAffected;

        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            rowsAffected = db.delete(table, whereClause, whereArgs);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            //LogManager.log(TAG, e.getClass() + " error: " + e.getMessage());
            throw e;
        } finally {
            db.endTransaction();
            db.close();
        }

        return rowsAffected;
    }

    public void execSql(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }
}