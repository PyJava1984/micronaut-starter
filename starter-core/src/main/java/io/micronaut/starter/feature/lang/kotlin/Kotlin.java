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
package io.micronaut.starter.feature.lang.kotlin;

import io.micronaut.starter.application.ApplicationType;
import io.micronaut.starter.application.generator.GeneratorContext;
import io.micronaut.starter.build.dependencies.Coordinate;
import io.micronaut.starter.build.dependencies.Dependency;
import io.micronaut.starter.feature.ApplicationFeature;
import io.micronaut.starter.feature.Feature;
import io.micronaut.starter.feature.FeatureContext;
import io.micronaut.starter.feature.lang.LanguageFeature;
import io.micronaut.starter.options.Language;
import io.micronaut.starter.options.Options;

import jakarta.inject.Singleton;
import java.util.List;
import java.util.Set;

@Singleton
public class Kotlin implements LanguageFeature {

    private final List<KotlinApplicationFeature> applicationFeatures;

    public Kotlin(List<KotlinApplicationFeature> applicationFeatures) {
        this.applicationFeatures = applicationFeatures;
    }

    @Override
    public String getName() {
        return "kotlin";
    }

    @Override
    public void processSelectedFeatures(FeatureContext featureContext) {
        if (!featureContext.isPresent(ApplicationFeature.class)) {
            applicationFeatures.stream()
                    .filter(f -> !f.isVisible() && f.supports(featureContext.getApplicationType()))
                    .findFirst()
                    .ifPresent(featureContext::addFeature);
        }
    }

    @Override
    public void apply(GeneratorContext generatorContext) {
        Coordinate coordinate = generatorContext.resolveCoordinate("kotlin-bom");
        generatorContext.getBuildProperties().put("kotlinVersion", coordinate.getVersion());
        Dependency.Builder kotlin = Dependency.builder()
                .groupId("org.jetbrains.kotlin")
                .compile()
                .version("${kotlinVersion}")
                .template();

        generatorContext.addDependency(kotlin.artifactId("kotlin-stdlib-jdk8"));
        generatorContext.addDependency(kotlin.artifactId("kotlin-reflect"));
        generatorContext.addDependency(Dependency.builder()
                .groupId("io.micronaut.kotlin")
                .artifactId("micronaut-kotlin-runtime")
                .compile());
    }

    @Override
    public boolean isKotlin() {
        return true;
    }

    @Override
    public boolean shouldApply(ApplicationType applicationType, Options options, Set<Feature> selectedFeatures) {
        return options.getLanguage() == Language.KOTLIN;
    }
}
