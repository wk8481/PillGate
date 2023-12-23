package be.kdg.programming3.pillgate.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.annotation.Profile;


import javax.sql.DataSource;
import java.util.Properties;
/**
 * This class { @code P2DConfig }provides Spring configuration for PostgreSQL database.
 * This class includes a DataSource bean definition for connecting to the PostgreSQL database
 * and is annotated with {@code @Profile("prod")} to indicate that it is applicable only in the "prod" profile.
 * The DataSource bean is configured with the PostgreSQL driver class name, database URL,
 * username, and password. These properties are specific to the production environment and should
 *  be adjusted accordingly.
 * @author Team PillGate
 */


@Configuration
@Profile("prod")
public class P2DConfig {
    @Bean
    public DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder
                .create()

                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql://localhost:5432/PillGatePG")
                .username("postgres")
                .password("Student_1234")
                .build();
        return dataSource;
    }
}


