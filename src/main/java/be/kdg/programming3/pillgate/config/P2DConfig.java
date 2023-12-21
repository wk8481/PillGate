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
                .url("jdbc:postgresql://localhost:5432/PillGatePG")
                .username("postgres")
                .password("student")
                .build();
        return dataSource;
    }
}

/*private final Environment environment;

    @Autowired
    public PostgresJdbcConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(getDataSource());
    }*/
