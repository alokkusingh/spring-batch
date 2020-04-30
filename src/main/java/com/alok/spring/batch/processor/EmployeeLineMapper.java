package com.alok.spring.batch.processor;

import com.alok.spring.batch.model.EmployeeDataBean;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.stereotype.Component;

@Component
public class EmployeeLineMapper extends DefaultLineMapper<EmployeeDataBean> {

    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    BeanWrapperFieldSetMapper<EmployeeDataBean> fieldSetMapper = new BeanWrapperFieldSetMapper<>();

    {
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"id", "name", "department", "salary"});

        fieldSetMapper.setTargetType(EmployeeDataBean.class);

        super.setLineTokenizer(lineTokenizer);
        super.setFieldSetMapper(fieldSetMapper);
    }
}
