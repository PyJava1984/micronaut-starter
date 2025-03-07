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
package io.micronaut.starter.feature.aws;

import io.micronaut.starter.feature.Category;
import io.micronaut.starter.feature.OneOfFeature;

public interface AwsLambdaEventFeature extends OneOfFeature, AwsFeature {

    @Override
    default Class<?> getFeatureClass() {
        return AwsLambdaEventFeature.class;
    }

    @Override
    default String getCategory() {
        return Category.CLOUD;
    }
}
