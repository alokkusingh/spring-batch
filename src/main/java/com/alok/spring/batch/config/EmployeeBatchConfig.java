package com.alok.spring.batch.config;

import com.alok.spring.batch.model.Employee;
import com.alok.spring.batch.model.EmployeeDataBean;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class EmployeeBatchConfig {

    @Bean("EmployeeJob")
    public Job employeeJob(JobBuilderFactory jobBuilderFactory,
                   StepBuilderFactory stepBuilderFactory,
                   ItemReader<EmployeeDataBean> itemReader,
                   ItemProcessor<EmployeeDataBean, Employee> itemProcessor,
                   ItemWriter<Employee> itemWriter
    ) {
        Step step = stepBuilderFactory.get("Employee-ETL-file-load")
                .<EmployeeDataBean,Employee>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return jobBuilderFactory.get("Employee-ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

   /* The below are moved to separate class as Spring Component
    @Bean
    public FlatFileItemReader<EmployeeDataBean> itemReader() {

        FlatFileItemReader<EmployeeDataBean> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/employee.csv"));
        flatFileItemReader.setName("CSV-Reader");
        flatFileItemReader.setLinesToSkip(1);
        flatFileItemReader.setLineMapper(lineMapper());
        flatFileItemReader.setStrict(false);

        return flatFileItemReader;
    }

    @Bean
    public LineMapper<EmployeeDataBean> lineMapper() {
        DefaultLineMapper<EmployeeDataBean> defaultLineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id", "name", "department", "salary"});

        BeanWrapperFieldSetMapper<EmployeeDataBean> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(EmployeeDataBean.class);

        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }*/
}
