package hxy.dream.common.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class HikariCPAutoConfiguration {

    @Bean
    @ConditionalOnAvailableEndpoint
    @ConditionalOnMissingBean
    public HikariCPEndpoint hikariCPEndpoint(DataSource dataSource) {
        HikariDataSource hikaridatasource = (HikariDataSource) dataSource;
        hikaridatasource.setRegisterMbeans(true);
        return new HikariCPEndpoint(hikaridatasource);
    }
}