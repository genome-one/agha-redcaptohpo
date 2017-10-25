package au.org.garvan.kccg.agha.redcap.config;

import au.org.garvan.kccg.agha.redcap.constants.RedCAPMappingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Created by tudor on 14/03/17.
 */
@Configuration
public class SectionDefinitionsConfig {

    private static final Logger log = LoggerFactory.getLogger(SectionDefinitionsConfig.class);

    @Autowired
    private Environment environment;

    @Bean
    public String sectionDefinitionsLocation() {
        log.info("Loading section definitions from: " + environment.getProperty(RedCAPMappingConstants.SECTION_DEFINITIONS));
        return environment.getProperty(RedCAPMappingConstants.SECTION_DEFINITIONS);
    }
}
