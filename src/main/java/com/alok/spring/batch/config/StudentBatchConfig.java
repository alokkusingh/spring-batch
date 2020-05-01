package com.alok.spring.batch.config;

import com.alok.spring.batch.model.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

@Configuration
@EnableBatchProcessing
public class StudentBatchConfig {
    @Value("${file.path.student:#{null}}")
    private String filePath;

    @Bean("StudentJob")
    public Job studentJob(JobBuilderFactory jobBuilderFactory,
                           StepBuilderFactory stepBuilderFactory,
                           ItemReader<Student> itemsReader,
                           ItemProcessor<Student, Student> itemProcessor,
                           ItemWriter<Student> itemWriter
    ) {
        Step step = stepBuilderFactory.get("Student-ETL-file-load")
                .<Student,Student>chunk(100)
                .reader(itemsReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get("Student-ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public MultiResourceItemReader<Student> itemsReader() {

        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = null;
        try {
            resources = patternResolver.getResources("file:" + filePath );
        } catch (IOException e) {
            e.printStackTrace();
        }
        MultiResourceItemReader<Student> reader = new MultiResourceItemReader<>();
        reader.setResources(resources);
        reader.setStrict(false);
        reader.setDelegate(itemReader());
        return reader;
    }

    @Bean
    public FlatFileItemReader<Student> itemReader() {

        FlatFileItemReader<Student> flatFileItemReader = new FlatFileItemReader<>();
        //flatFileItemReader.setResource(new FileSystemResource("src/main/resources/student.csv"));
        flatFileItemReader.setName("Student-CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        flatFileItemReader.setStrict(false);

        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Student> lineMapper() {
        DefaultLineMapper<Student> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id", "name", "department", "marks"});

        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Student.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
