package school.raikes.library.libraryserver.startup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Profile("dev")
@Slf4j
public class CsvImportApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.error("This is running as expected.");
        log.error(Arrays.toString(args.getSourceArgs()));
        for (String str : args.getOptionNames()) {
            log.warn(str);
        }
    }
}
