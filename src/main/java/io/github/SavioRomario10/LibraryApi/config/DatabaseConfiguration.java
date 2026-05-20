package io.github.SavioRomario10.LibraryApi.config;

import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatabaseConfiguration {

  @Value("${spring.datasource.url}")
  String url;
  @Value("${spring.datasource.username}")
  String username;
  @Value("${spring.datasource.password}")
  String password;
  @Value("${spring.datasource.driver-class-name}")
  Optional<String> driver;

  @Bean
  public DataSource dataSource(){
    DriverManagerDataSource ds = new DriverManagerDataSource();

    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(password);
    driver.ifPresent(ds::setDriverClassName);

    return ds;
  }

  // @Bean
  public DataSource hikariDataSource(){

    HikariConfig config = new HikariConfig();

    config.setJdbcUrl(url);
    config.setUsername(username);
    config.setPassword(password);
    driver.ifPresent(config::setDriverClassName);

    config.setMaximumPoolSize(10);
    config.setMinimumIdle(1);
    config.setPoolName("library-db-pool");
    config.setMaxLifetime(600000);
    config.setConnectionTimeout(100000);
    config.setConnectionTestQuery("select 1");

    return new HikariDataSource(config);
  }
}
