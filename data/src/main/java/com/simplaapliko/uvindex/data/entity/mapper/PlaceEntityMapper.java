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

package com.simplaapliko.uvindex.data.entity.mapper;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.simplaapliko.uvindex.data.entity.PlaceEntity;
import com.simplaapliko.uvindex.domain.model.Place;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlaceEntityMapper {

    /**
     * Maps list of {@link PlaceEntity} to list of {@link Place}.
     * If the list of {@link PlaceEntity} is null, returns {@link Collections#emptyList()}
     *
     * @param entities list of {@link PlaceEntity} to be mapped to {@link Place}
     * @return the list of mapped {@link Place}
     */
    @NonNull
    public List<Place> toModels(@Nullable List<PlaceEntity> entities) {
        List<Place> places;
        if (entities != null) {
            places = new ArrayList<>();
            for (PlaceEntity entity : entities) {
                Place place = toModel(entity);
                if (place != null) {
                    places.add(place);
                }
            }
        } else {
            places = Collections.emptyList();
        }
        return places;
    }

    /**
     * Maps {@link PlaceEntity} to {@link Place}.
     *
     * @param entity {@link PlaceEntity} to be mapped to {@link Place}
     * @return the mapped {@link Place} or null
     */
    @Nullable
    public Place toModel(PlaceEntity entity) {
        Place place = null;
        if (entity != null) {
            place = new Place.Builder().setId(entity.getId())
                    .setOrder(entity.getOrder())
                    .setName(entity.getName())
                    .setCountry(entity.getCountry())
                    .setLatitude(entity.getLatitude())
                    .setLongitude(entity.getLongitude())
                    .setTimeZoneId(entity.getTimeZoneId())
                    .build();
        }
        return place;
    }

    /**
     * Maps {@link Place} to {@link PlaceEntity}.
     *
     * @param model {@link Place} to be mapped to {@link PlaceEntity}
     * @return the mapped {@link PlaceEntity} or null
     */
    @Nullable
    public PlaceEntity toEntity(Place model) {
        PlaceEntity entity = null;
        if (model != null) {
            entity = new PlaceEntity.Builder().setId(model.getId())
                    .setOrder(model.getOrder())
                    .setName(model.getName())
                    .setCountry(model.getCountry())
                    .setLatitude(model.getLatitude())
                    .setLongitude(model.getLongitude())
                    .setTimeZoneId(model.getTimeZoneId())
                    .build();
        }
        return entity;
    }
}
