package be.kdg.programming3.pillgate.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


import javax.sql.DataSource;


/**
 * Configuration class for the PillGate application in a development environment.
 * Specifies the data source properties for connecting to the H2 in-memory database.
 *
 * @author Team PillGate
 * @see DataSourceBuilder
 * @see DataSource
 */
@Configuration
@Profile("dev")
public class H2DBConfig {

    /**
     * Configures and returns the data source for the PillGate application in a development environment.
     *
     * @return The configured data source.
     */
    @Bean
    public DataSource dataSource(){
        DataSource dataSource = DataSourceBuilder
                .create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:PillGatedb")
                .username("sa")
                .password("")
                .build();
        return dataSource;
    }





}
