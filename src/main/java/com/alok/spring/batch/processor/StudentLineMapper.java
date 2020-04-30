package com.alok.spring.batch.processor;

import com.alok.spring.batch.model.EmployeeDataBean;
import com.alok.spring.batch.model.Student;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Component;

@Component
public class StudentLineMapper extends DefaultLineMapper<Student> {

    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

    {
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id", "name", "department", "marks"});

        fieldSetMapper.setTargetType(Student.class);

        super.setLineTokenizer(lineTokenizer);
        super.setFieldSetMapper(fieldSetMapper);
    }
}
