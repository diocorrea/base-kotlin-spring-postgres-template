// Configure the Java plugin and the dependencies
// ----------------------------------------------
apply plugin: 'java'

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    /* Use org.jooq                for the Open Source Edition
           org.jooq.pro            for commercial editions with Java 17 support,
           org.jooq.pro-java-11    for commercial editions with Java 11 support,
           org.jooq.pro-java-8     for commercial editions with Java 8 support,
           org.jooq.trial          for the free trial edition with Java 17 support,
           org.jooq.trial-java-11  for the free trial edition with Java 11 support,
           org.jooq.trial-java-8   for the free trial edition with Java 8 support */
    compile 'org.jooq:jooq:3.17.5'

    runtime 'com.h2database:h2:1.4.200'
    testCompile 'junit:junit:4.11'
}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        /* See above for the correct groupId */
        classpath 'org.jooq:jooq-codegen:3.17.5'
        classpath 'com.h2database:h2:200'
    }
}

import org.jooq.codegen.GenerationTool
        import org.jooq.meta.jaxb.*

        GenerationTool.generate(new Configuration()
            .withJdbc(new Jdbc()
                .withDriver('org.h2.Driver')
                .withUrl('jdbc:h2:~/test-gradle')
                .withUser('sa')
                .withPassword(''))
            .withGenerator(new Generator()
                .withDatabase(new Database())
                .withGenerate(new Generate()
                    .withPojos(true)
                    .withDaos(true))
                .withTarget(new Target()
                    .withPackageName('org.jooq.example.gradle.db')
                    .withDirectory('src/main/java'))))
