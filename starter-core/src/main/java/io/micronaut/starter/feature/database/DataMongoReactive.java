/*
 * Copyright 2017-2022 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.starter.feature.database;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.starter.feature.testresources.TestResources;
import jakarta.inject.Singleton;

/**
 * Add support for Micronaut Data MongoDB Reactive.
 */
@Singleton
public class DataMongoReactive extends DataMongoFeature {

    private static final String ASYNC_MONGODB_ARTIFACT = "mongodb-driver-reactivestreams";

    public DataMongoReactive(Data data,
                             TestContainers testContainers,
                             TestResources testResources) {
        super(data, testContainers, testResources);
    }

    @NonNull
    @Override
    public String getName() {
        return "data-mongodb-reactive";
    }

    @NonNull
    @Override
    public String getDescription() {
        return "Adds support for defining reactive data repositories for MongoDB";
    }

    @Override
    public String getTitle() {
        return "Micronaut Data MongoDB Reactive";
    }

    @NonNull
    @Override
    protected String mongoArtifact() {
        return ASYNC_MONGODB_ARTIFACT;
    }
}
