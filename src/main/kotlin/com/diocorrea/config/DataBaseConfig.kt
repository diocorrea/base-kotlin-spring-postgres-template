package com.diocorrea.config

import org.jooq.SQLDialect
import org.jooq.impl.DefaultConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
class DataBaseConfig {
    @Bean
    fun configuration(dataSource: DataSource): DefaultConfiguration {
        val jooqConfiguration = DefaultConfiguration()
        jooqConfiguration.set(dataSource)
        jooqConfiguration.set(SQLDialect.POSTGRES)
        return jooqConfiguration
    }
}
