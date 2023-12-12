package heig.bdr.choochoo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

@Import(ChooChooApplication.class)
public class ReferenceDataRunner implements CommandLineRunner {

    public static void main(String[] args) {
        System.setProperty("spring.main.web-application-type", "none");
        System.setProperty("spring.main.web-environment", "false");
        SpringApplication.run(ReferenceDataRunner.class, args);
    }

    private final JobLauncher jobLauncher;
    private final Job referenceDataJob;

    public ReferenceDataRunner(
            JobLauncher jobLauncher,
            Job referenceDataJob
    ) {
        this.jobLauncher = jobLauncher;
        this.referenceDataJob = referenceDataJob;
    }

    @Override
    public void run(String... args) throws Exception {
        jobLauncher.run(referenceDataJob, new JobParameters());
    }
}
