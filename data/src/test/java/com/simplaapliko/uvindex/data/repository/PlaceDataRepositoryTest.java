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
import com.simplaapliko.uvindex.data.repository.datasource.place.PlaceDataSource;
import com.simplaapliko.uvindex.domain.model.Place;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.observers.TestObserver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PlaceDataRepositoryTest {

    @Mock private PlaceDataSource mockPlaceDataSource;
    @Mock private PlaceEntityMapper mockPlaceEntityMapper;
    private PlaceDataRepository mPlaceDataRepository;

    @Before
    public void setUp() {
        mPlaceDataRepository = new PlaceDataRepository(mockPlaceDataSource, mockPlaceEntityMapper);
    }

    @Test
    public void placeById_success() {
        Place mockPlace = mock(Place.class);
        PlaceEntity mockPlaceEntity = mock(PlaceEntity.class);

        long placeId = 7L;
        when(mockPlaceDataSource.placeById(placeId)).thenReturn(mockPlaceEntity);
        when(mockPlaceEntityMapper.toModel(mockPlaceEntity)).thenReturn(mockPlace);

        TestObserver<Place> testObserver = new TestObserver<>();
        mPlaceDataRepository.placeById(placeId)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        testObserver.assertValue(mockPlace);
    }

    @Test
    public void placeById_notFound() {
        long placeId = 7L;
        when(mockPlaceDataSource.placeById(placeId)).thenReturn(null);

        TestObserver<Place> testObserver = new TestObserver<>();
        mPlaceDataRepository.placeById(placeId)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(0);
        testObserver.assertNoValues();
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    public void allPlaces_success() {
        Place mockPlace = mock(Place.class);
        List<Place> mockPlaces = Arrays.asList(mockPlace);
        PlaceEntity mockPlaceEntity = mock(PlaceEntity.class);
        List<PlaceEntity> mockPlaceEntities = Arrays.asList(mockPlaceEntity);

        when(mockPlaceDataSource.allPlaces()).thenReturn(mockPlaceEntities);
        when(mockPlaceEntityMapper.toModels(mockPlaceEntities)).thenReturn(mockPlaces);

        TestObserver<List<Place>> testObserver = new TestObserver<>();
        mPlaceDataRepository.allPlaces()
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        testObserver.assertValue(mockPlaces);
    }

    @Test
    public void allPlaces_noData() {
        List<Place> mockPlaces = new ArrayList<>();
        List<PlaceEntity> mockPlaceEntities = new ArrayList<>();

        when(mockPlaceDataSource.allPlaces()).thenReturn(mockPlaceEntities);
        when(mockPlaceEntityMapper.toModels(mockPlaceEntities)).thenReturn(mockPlaces);

        TestObserver<List<Place>> testObserver = new TestObserver<>();
        mPlaceDataRepository.allPlaces()
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(1);
        testObserver.assertValue(mockPlaces);
    }

    @Test
    public void add_success() {
        Place mockPlace = mock(Place.class);
        PlaceEntity mockPlaceEntity = mock(PlaceEntity.class);

        long placeId = 7L;
        when(mockPlaceDataSource.save(mockPlaceEntity)).thenReturn(placeId);
        when(mockPlaceEntityMapper.toEntity(mockPlace)).thenReturn(mockPlaceEntity);

        TestObserver<List<Place>> testObserver = new TestObserver<>();
        mPlaceDataRepository.add(mockPlace)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(0);
        testObserver.assertNoValues();
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void add_nullEntity() {
        Place mockPlace = null;
        PlaceEntity mockPlaceEntity = null;

        long placeId = 7L;
        when(mockPlaceDataSource.save(null)).thenReturn(placeId);
        when(mockPlaceEntityMapper.toEntity(mockPlace)).thenReturn(mockPlaceEntity);

        TestObserver<List<Place>> testObserver = new TestObserver<>();
        mPlaceDataRepository.add(mockPlace)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(0);
        testObserver.assertNoValues();
    }

    @Test
    public void delete_success() {
        long placeId = 7L;
        int rowCount = 1;
        when(mockPlaceDataSource.delete(placeId)).thenReturn(rowCount);

        TestObserver<List<Place>> testObserver = new TestObserver<>();
        mPlaceDataRepository.delete(placeId)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(0);
        testObserver.assertNoValues();
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void delete_noEntitiesDeleted() {
        long placeId = 7L;
        int rowCount = 0;
        when(mockPlaceDataSource.delete(placeId)).thenReturn(rowCount);

        TestObserver<List<Place>> testObserver = new TestObserver<>();
        mPlaceDataRepository.delete(placeId)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(0);
        testObserver.assertNoValues();
    }

    @Test
    public void update_success() {
        Place mockPlace = mock(Place.class);
        PlaceEntity mockPlaceEntity = mock(PlaceEntity.class);

        int rowCount = 0;
        when(mockPlaceDataSource.update(mockPlaceEntity)).thenReturn(rowCount);
        when(mockPlaceEntityMapper.toEntity(mockPlace)).thenReturn(mockPlaceEntity);

        TestObserver<List<Place>> testObserver = new TestObserver<>();
        mPlaceDataRepository.update(mockPlace)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(0);
        testObserver.assertNoValues();
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void update_noEntitiesUpdated() {
        Place mockPlace = null;
        PlaceEntity mockPlaceEntity = null;

        int rowCount = 0;
        when(mockPlaceDataSource.update(null)).thenReturn(rowCount);
        when(mockPlaceEntityMapper.toEntity(mockPlace)).thenReturn(mockPlaceEntity);

        TestObserver<List<Place>> testObserver = new TestObserver<>();
        mPlaceDataRepository.update(mockPlace)
                .subscribe(testObserver);

        testObserver.awaitTerminalEvent();
        testObserver.assertComplete();
        testObserver.assertNoErrors();
        testObserver.assertValueCount(0);
        testObserver.assertNoValues();
    }
}
