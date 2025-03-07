package io.micronaut.starter.feature.elasticsearch

import io.micronaut.starter.ApplicationContextSpec
import io.micronaut.starter.BuildBuilder
import io.micronaut.starter.application.generator.GeneratorContext
import io.micronaut.starter.fixture.CommandOutputFixture
import io.micronaut.starter.options.BuildTool
import io.micronaut.starter.options.Language
import spock.lang.Requires
import spock.lang.Unroll

class ElasticsearchSpec extends ApplicationContextSpec  implements CommandOutputFixture {

    void 'test readme.md with feature elasticsearch contains links to micronaut docs'() {
        when:
        def output = generate(['elasticsearch'])
        def readme = output["README.md"]

        then:
        readme
        readme.contains("https://micronaut-projects.github.io/micronaut-elasticsearch/latest/guide/index.html")
    }

    @Unroll
    void 'test gradle elasticsearch feature for language=#language'() {
        when:
        String template = new BuildBuilder(beanContext, BuildTool.GRADLE)
                .features(['elasticsearch'])
                .language(language)
                .render()

        then:
        template.contains('implementation("io.micronaut.elasticsearch:micronaut-elasticsearch")')

        where:
        language << Language.values().toList()
    }

    @Unroll
    void 'test maven elasticsearch feature for language=#language'() {
        when:
        String template = new BuildBuilder(beanContext, BuildTool.MAVEN)
                .language(language)
                .features(['elasticsearch'])
                .render()

        then:
        template.contains("""
    <dependency>
      <groupId>io.micronaut.elasticsearch</groupId>
      <artifactId>micronaut-elasticsearch</artifactId>
      <scope>compile</scope>
    </dependency>
""")

        where:
        language << Language.values().toList()
    }

    void 'test elasticsearch configuration'() {
        when:
        GeneratorContext commandContext = buildGeneratorContext(['elasticsearch'])

        then:
        commandContext.configuration.get('elasticsearch.httpHosts'.toString()) == 'http://localhost:9200,http://127.0.0.2:9200'
    }

    @Unroll
    @Requires({ jvm.isJava8() || jvm.isJava11() })
    void 'test gradle elasticsearch and graalvm features for language=#language'() {
        when:
        String template = new BuildBuilder(beanContext, BuildTool.GRADLE)
                .features(['elasticsearch', 'graalvm'])
                .language(language)
                .render()

        then:
        template.contains('runtimeOnly("org.slf4j:log4j-over-slf4j:')
        template.contains('implementation("org.apache.logging.log4j:log4j-api:')
        template.contains('implementation("org.apache.logging.log4j:log4j-core:')

        where:
        language << Language.values().toList() - Language.GROOVY
    }

    @Unroll
    @Requires({ jvm.isJava8() || jvm.isJava11() })
    void 'test maven elasticsearch and graalvm features for language=#language'() {
        when:
        String template = new BuildBuilder(beanContext, BuildTool.MAVEN)
                .language(language)
                .features(['elasticsearch', 'graalvm'])
                .render()

        then:
        template.contains('''\
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>log4j-over-slf4j</artifactId>''')
        template.contains('''\
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-api</artifactId>''')
        template.contains('''\
    <dependency>
      <groupId>org.apache.logging.log4j</groupId>
      <artifactId>log4j-core</artifactId>
      <version>''')

        where:
        language << Language.values().toList() - Language.GROOVY
    }
}
