package heig.bdr.choochoo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({
        @PropertySource(value = "file:.env.properties", ignoreResourceNotFound = true),
})
public class EnvironmentPropertiesConfiguration {
}
