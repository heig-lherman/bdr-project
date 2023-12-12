package heig.bdr.choochoo.configuration;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

@Primary
@AutoConfiguration
@ConfigurationProperties("spring.datasource")
public class PrimaryDataSourceConfiguration extends DataSourceProperties {
}
