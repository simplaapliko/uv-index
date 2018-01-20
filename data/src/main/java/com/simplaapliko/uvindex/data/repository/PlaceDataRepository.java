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

package com.simplaapliko.uvindex.data.repository;

import com.simplaapliko.uvindex.data.entity.PlaceEntity;
import com.simplaapliko.uvindex.data.entity.mapper.PlaceEntityMapper;
import com.simplaapliko.uvindex.data.repository.datasource.PlaceDataSource;
import com.simplaapliko.uvindex.domain.model.Place;
import com.simplaapliko.uvindex.domain.repository.PlaceRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class PlaceDataRepository implements PlaceRepository {

    private final PlaceDataSource mPlaceDataSource;
    private final PlaceEntityMapper mPlaceEntityMapper;

    public PlaceDataRepository(PlaceDataSource placeDataSource,
            PlaceEntityMapper placeEntityMapper) {
        mPlaceDataSource = placeDataSource;
        mPlaceEntityMapper = placeEntityMapper;
    }

    @Override
    public Maybe<Place> placeById(long id) {
        return Maybe.fromCallable(() -> mPlaceDataSource.selectById(id))
                .map(mPlaceEntityMapper::toModel)
                .onErrorResumeNext(Maybe.empty());
    }

    @Override
    public Single<List<Place>> allPlaces() {
        return Single.fromCallable(mPlaceDataSource::select)
                .map(mPlaceEntityMapper::toModels);
    }

    @Override
    public Completable add(Place place) {
        return Completable.fromCallable(() -> {
            PlaceEntity entity = mPlaceEntityMapper.toEntity(place);
            return mPlaceDataSource.insert(entity);
        });
    }

    @Override
    public Completable update(Place place) {
        return Completable.fromCallable(() -> {
            PlaceEntity entity = mPlaceEntityMapper.toEntity(place);
            return mPlaceDataSource.update(entity);
        });
    }

    @Override
    public Completable delete(long id) {
        return Completable.fromCallable(() -> mPlaceDataSource.delete(id));
    }
}
