package au.org.garvan.kccg.agha.redcap;

import au.org.garvan.kccg.agha.redcap.constants.SystemProperties;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by joshua on 4/12/2016.
 */
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        log.info("[" + DateTime.now() + "] Initializing: " + environment.getProperty(SystemProperties.SERVER_NAME));
    }

    @PreDestroy
    public void destroy() {
        log.info("[" + DateTime.now() + "] Shutting down: " + environment.getProperty(SystemProperties.SERVER_NAME));
    }

    public static void main(String[] args) throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String logFileName = System.getenv(SystemProperties.LOG_FILE_NAME);
        System.setProperty("log.file.name", logFileName + "-" + hostAddress);
        SpringApplication.run(Application.class, args);
    }
}
