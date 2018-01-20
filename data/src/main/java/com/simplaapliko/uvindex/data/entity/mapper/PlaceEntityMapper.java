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

import com.simplaapliko.uvindex.data.entity.PlaceEntity;
import com.simplaapliko.uvindex.domain.model.Place;

import java.util.ArrayList;
import java.util.List;

public class PlaceEntityMapper {

    public List<Place> toModels(List<PlaceEntity> entities) {
        List<Place> places = new ArrayList<>();
        if (entities != null) {
            for (PlaceEntity entity : entities) {
                places.add(toModel(entity));
            }
        }
        return places;
    }

    public Place toModel(PlaceEntity entity) {
        Place place = null;
        if (entity != null) {
            place = new Place.Builder().setId(entity.getId())
                    .setOrder(entity.getOrder())
                    .setName(entity.getName())
                    .setCountry(entity.getCountry())
                    .setLatitude(entity.getLatitude())
                    .setLongitude(entity.getLongitude())
                    .build();
        }
        return place;
    }

    public PlaceEntity toEntity(Place model) {
        PlaceEntity entity = null;
        if (model != null) {
            entity = new PlaceEntity.Builder().setId(model.getId())
                    .setOrder(model.getOrder())
                    .setName(model.getName())
                    .setCountry(model.getCountry())
                    .setLatitude(model.getLatitude())
                    .setLongitude(model.getLongitude())
                    .build();
        }
        return entity;
    }
}
