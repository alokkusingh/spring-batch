package com.alok.spring.batch.config;

import com.alok.spring.batch.model.Student;
import com.alok.spring.batch.processor.FileArchiveTasklet;
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

@Configuration
@EnableBatchProcessing
public class StudentBatchConfig {
    @Value("${file.path.student:#{null}}")
    private String filePath;

    @Value("file:${file.path.student}")
    private Resource[] resources;

    @Value("${fields.name.student:#{null}}")
    private String[] fieldNames;

    @Bean("StudentJob")
    public Job studentJob(JobBuilderFactory jobBuilderFactory,
                           StepBuilderFactory stepBuilderFactory,
                           ItemReader<Student> itemsReader,
                           ItemProcessor<Student, Student> itemProcessor,
                           ItemWriter<Student> itemWriter
    ) {
        Step step1 = stepBuilderFactory.get("Student-ETL-file-load")
                .<Student,Student>chunk(100)
                .reader(itemsReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();


        FileArchiveTasklet archiveTask = new FileArchiveTasklet();
        archiveTask.setResources(resources);
        Step step2 = stepBuilderFactory.get("Student-ETL-file-archive")
                .tasklet(archiveTask)
                .build();

        return jobBuilderFactory.get("Student-ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .next(step2)
                .build();
    }



    @Bean
    public MultiResourceItemReader<Student> itemsReader() {

        /*ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = null;
        try {
            resources = patternResolver.getResources("file:" + filePath );
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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
        //lineTokenizer.setNames(new String[]{"id", "name", "department", "marks"});
        lineTokenizer.setNames(fieldNames);

        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Student.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
