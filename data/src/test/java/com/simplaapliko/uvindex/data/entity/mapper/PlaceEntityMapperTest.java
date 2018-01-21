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

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import static com.google.common.truth.Truth.assertThat;

public class PlaceEntityMapperTest {

    private PlaceEntityMapper mPlaceEntityMapper;

    @Before
    public void setUp() {
        mPlaceEntityMapper = new PlaceEntityMapper();
    }

    @Test
    public void toEntity() {
        Place place = new Place.Builder().setId(7)
                .setOrder(99)
                .setName("name")
                .setCountry("country")
                .setLatitude(14)
                .setLongitude(41)
                .setTimeZoneId(TimeZone.getDefault()
                        .getID())
                .build();

        PlaceEntity actualResult = mPlaceEntityMapper.toEntity(place);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(place.getId());
        assertThat(actualResult.getOrder()).isEqualTo(place.getOrder());
        assertThat(actualResult.getName()).isEqualTo(place.getName());
        assertThat(actualResult.getCountry()).isEqualTo(place.getCountry());
        assertThat(actualResult.getLatitude()).isEqualTo(place.getLatitude());
        assertThat(actualResult.getLongitude()).isEqualTo(place.getLongitude());
        assertThat(actualResult.getTimeZoneId()).isEqualTo(place.getTimeZoneId());
    }

    @Test
    public void toEntity_null() {
        PlaceEntity actualResult = mPlaceEntityMapper.toEntity(null);
        assertThat(actualResult).isNull();
    }

    @Test
    public void toModel() {
        PlaceEntity entity = fakePlaceEntity();

        Place actualResult = mPlaceEntityMapper.toModel(entity);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getId()).isEqualTo(entity.getId());
        assertThat(actualResult.getOrder()).isEqualTo(entity.getOrder());
        assertThat(actualResult.getName()).isEqualTo(entity.getName());
        assertThat(actualResult.getCountry()).isEqualTo(entity.getCountry());
        assertThat(actualResult.getLatitude()).isEqualTo(entity.getLatitude());
        assertThat(actualResult.getLongitude()).isEqualTo(entity.getLongitude());
        assertThat(actualResult.getTimeZoneId()).isEqualTo(entity.getTimeZoneId());
    }

    @Test
    public void toModel_null() {
        Place actualResult = mPlaceEntityMapper.toModel(null);
        assertThat(actualResult).isNull();
    }

    @Test
    public void toModels() {
        List<PlaceEntity> entities = new ArrayList<>();
        entities.add(fakePlaceEntity());
        entities.add(fakePlaceEntity());

        List<Place> actualResult = mPlaceEntityMapper.toModels(entities);
        assertThat(actualResult).hasSize(2);
    }

    @Test
    public void toModels_emptyList() {
        List<PlaceEntity> entities = new ArrayList<>();

        List<Place> actualResult = mPlaceEntityMapper.toModels(entities);
        assertThat(actualResult).hasSize(0);
    }

    @Test
    public void toModels_listOfNulls() {
        List<PlaceEntity> entities = new ArrayList<>();
        entities.add(null);
        entities.add(null);

        List<Place> actualResult = mPlaceEntityMapper.toModels(entities);
        assertThat(actualResult).hasSize(0);
    }

    @Test
    public void toModels_null() {
        List<Place> actualResult = mPlaceEntityMapper.toModels(null);
        assertThat(actualResult).isEqualTo(Collections.emptyList());
    }

    private PlaceEntity fakePlaceEntity() {
        return new PlaceEntity.Builder().setId(7L)
                .setOrder(99)
                .setName("name")
                .setCountry("country")
                .setLatitude(14D)
                .setLongitude(41D)
                .setTimeZoneId(TimeZone.getDefault()
                        .getID())
                .build();
    }
}
