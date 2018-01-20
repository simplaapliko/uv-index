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

package com.simplaapliko.uvindex.data.repository.datasource;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.simplaapliko.uvindex.data.entity.PlaceEntity;
import com.simplaapliko.uvindex.data.sqlite.Database;
import com.simplaapliko.uvindex.data.sqlite.entity.PlaceSqliteEntity;
import com.simplaapliko.uvindex.data.sqlite.table.PlaceTable;

import java.util.ArrayList;
import java.util.List;

public class PlaceSqliteDataSource implements PlaceDataSource {

    private final Database mDatabase;

    public PlaceSqliteDataSource(Database database) {
        mDatabase = database;
    }

    @NonNull
    @Override
    public List<PlaceEntity> select() {
        List<PlaceEntity> list = new ArrayList<>();

        String query = PlaceTable.QUERY + " ORDER BY " + PlaceTable.PLACE_ORDER + ", "
                + PlaceTable.PLACE_ID;

        Cursor cursor = mDatabase.query(query, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    list.add(PlaceTable.MAPPER.apply(cursor));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    @NonNull
    @Override
    public List<PlaceEntity> selectOrderByName() {
        List<PlaceEntity> list = new ArrayList<>();

        String query = PlaceTable.QUERY + " ORDER BY " + PlaceTable.PLACE_NAME;

        Cursor cursor = mDatabase.query(query, null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    list.add(PlaceTable.MAPPER.apply(cursor));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    @Nullable
    @Override
    public PlaceEntity selectById(long id) {
        PlaceEntity entity = null;

        String query =
                PlaceTable.QUERY + " WHERE " + PlaceTable.PLACE_ID + "=" + String.valueOf(id);

        Cursor cursor = mDatabase.query(query, null);

        if (cursor.moveToFirst()) {
            try {
                entity = PlaceTable.MAPPER.apply(cursor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        cursor.close();
        return entity;
    }

    @Override
    public long insert(@Nullable PlaceEntity entity) {
        return mDatabase.insert(PlaceTable.TABLE, new PlaceSqliteEntity(entity));
    }

    @Override
    public int update(@Nullable PlaceEntity entity) {
        return mDatabase.update(PlaceTable.TABLE, new PlaceSqliteEntity(entity));
    }

    @Override
    public int delete(@Nullable PlaceEntity entity) {
        if (entity == null) {
            return 0;
        }
        return delete(entity.getId());
    }

    @Override
    public int delete(long id) {
        return mDatabase.delete(PlaceTable.TABLE, id);
    }
}
