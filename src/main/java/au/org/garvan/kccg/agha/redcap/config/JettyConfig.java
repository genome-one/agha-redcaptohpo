package au.org.garvan.kccg.agha.redcap.config;

import au.org.garvan.kccg.agha.redcap.constants.SystemProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by joshua on 4/12/2016.
 */
@Configuration
public class JettyConfig {
    @Autowired
    private Environment environment;


    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        JettyEmbeddedServletContainerFactory factory = new JettyEmbeddedServletContainerFactory();
        int port = Integer.parseInt(environment.getProperty(SystemProperties.SERVER_PORT));
        factory.setPort(port);
        return factory;
    }
}
