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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.simplaapliko.uvindex.data.entity.PlaceEntity;

import java.util.List;

public interface PlaceDataSource {

    /**
     * Returns the non-null list of places.
     *
     * @return the list of places
     */
    @NonNull
    List<PlaceEntity> select();

    /**
     * Returns the non-null list of places ordered by name.
     *
     * @return the list of places ordered by name
     */
    @NonNull
    List<PlaceEntity> selectOrderByName();

    /**
     * Returns the entity for the provided id.
     *
     * @param id id of the entity to return
     * @return the entity for the provided id
     */
    @Nullable
    PlaceEntity selectById(final long id);

    /**
     * Returns id of the inserted entity. Returns -1 if entity is null.
     *
     * @param entity entity to be inserted
     * @return id of the inserted entity
     */
    long insert(@Nullable final PlaceEntity entity);

    /**
     * Returns the count of the updated entities. Returns 0 if entity is null.
     *
     * @param entity entity to be updated
     * @return the count of the updated entities
     */
    int update(@Nullable final PlaceEntity entity);

    /**
     * Returns the count of the deleted entities. Returns 0 if entity is null.
     *
     * @param entity entity to be deleted
     * @return the count of the deleted entities
     */
    int delete(@Nullable final PlaceEntity entity);

    /**
     * Returns the count of the deleted entities.
     *
     * @param id id of the entity to delete
     * @return the count of the deleted entities
     */
    int delete(final long id);
}
