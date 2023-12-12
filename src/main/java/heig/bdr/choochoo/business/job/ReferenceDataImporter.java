package heig.bdr.choochoo.business.job;

import com.fasterxml.jackson.databind.ObjectMapper;
import heig.bdr.choochoo.business.job.mapper.LineItemProcessor;
import heig.bdr.choochoo.business.job.mapper.SegmentItemProcessor;
import heig.bdr.choochoo.business.job.mapper.StationItemProcessor;
import heig.bdr.choochoo.business.model.Line;
import heig.bdr.choochoo.business.model.Segment;
import heig.bdr.choochoo.business.model.Station;
import heig.bdr.choochoo.connector.opendata.ApiLine;
import heig.bdr.choochoo.connector.opendata.ApiSegment;
import heig.bdr.choochoo.connector.opendata.ApiStation;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ReferenceDataImporter {

    @Bean
    public JsonItemReader<ApiStation> stationReader(
            ObjectMapper objectMapper
    ) {
        return new JsonItemReaderBuilder<ApiStation>()
                .name("station-reader")
                .resource(new ClassPathResource("/data/stations.json"))
                .jsonObjectReader(new JacksonJsonObjectReader<>(objectMapper, ApiStation.class))
                .strict(true)
                .build();
    }

    @Bean
    public JsonItemReader<ApiLine> lineReader(
            ObjectMapper objectMapper
    ) {
        return new JsonItemReaderBuilder<ApiLine>()
                .name("line-reader")
                .resource(new ClassPathResource("/data/lines.json"))
                .jsonObjectReader(new JacksonJsonObjectReader<>(objectMapper, ApiLine.class))
                .strict(true)
                .build();
    }

    @Bean
    public JsonItemReader<ApiSegment> segmentReader(
            ObjectMapper objectMapper
    ) {
        return new JsonItemReaderBuilder<ApiSegment>()
                .name("segment-reader")
                .resource(new ClassPathResource("/data/segments.json"))
                .jsonObjectReader(new JacksonJsonObjectReader<>(objectMapper, ApiSegment.class))
                .strict(true)
                .build();
    }

    @Bean
    public JpaItemWriter<Station> stationWriter(
            EntityManagerFactory entityManagerFactory
    ) {
        return new JpaItemWriterBuilder<Station>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public JpaItemWriter<Line> lineWriter(
            EntityManagerFactory entityManagerFactory
    ) {
        return new JpaItemWriterBuilder<Line>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public JpaItemWriter<Segment> segmentWriter(
            EntityManagerFactory entityManagerFactory
    ) {
        return new JpaItemWriterBuilder<Segment>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step stationStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JsonItemReader<ApiStation> stationReader,
            StationItemProcessor stationProcessor,
            JpaItemWriter<Station> stationWriter
    ) {
        return new StepBuilder("station-import", jobRepository)
                .<ApiStation, Station>chunk(25, transactionManager)
                .reader(stationReader)
                .processor(stationProcessor)
                .writer(stationWriter)
                .build();
    }

    @Bean
    public Step lineStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JsonItemReader<ApiLine> lineReader,
            LineItemProcessor lineProcessor,
            JpaItemWriter<Line> lineWriter
    ) {
        return new StepBuilder("line-import", jobRepository)
                .<ApiLine, Line>chunk(25, transactionManager)
                .reader(lineReader)
                .processor(lineProcessor)
                .writer(lineWriter)
                .build();
    }

    @Bean
    public Step segmentStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            JsonItemReader<ApiSegment> segmentReader,
            SegmentItemProcessor segmentProcessor,
            JpaItemWriter<Segment> segmentWriter
    ) {
        return new StepBuilder("segment-import", jobRepository)
                .<ApiSegment, Segment>chunk(25, transactionManager)
                .reader(segmentReader)
                .processor(segmentProcessor)
                .writer(segmentWriter)
                .build();
    }

    @Bean
    public Job referenceDataJob(
            JobRepository jobRepository,
            Step stationStep,
            Step lineStep,
            Step segmentStep
    ) {
        return new JobBuilder("reference-data-importer", jobRepository)
                .start(stationStep)
                .next(lineStep)
                .next(segmentStep)
                .build();
    }
}
