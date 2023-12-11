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


@Configuration
@Profile("prod")
public class P2DConfig {
    @Bean
    public DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder
                .create()

                .driverClassName("org.postgresql.Driver")
                .url("jdbc:postgresql:pro3_db")
                .username("postgres")
                .password("student_1234")
                .build();
        return dataSource;
    }
}
