package heig.bdr.choochoo.configuration;

import com.fasterxml.jackson.databind.Module;
import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Module jtsModule() {
        return new JtsModule();
    }
}