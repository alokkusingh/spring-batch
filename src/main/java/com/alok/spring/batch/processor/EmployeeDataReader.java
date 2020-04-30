package com.alok.spring.batch.processor;

import com.alok.spring.batch.model.EmployeeDataBean;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDataReader extends FlatFileItemReader<EmployeeDataBean> {

    @Autowired
    public EmployeeDataReader(LineMapper<EmployeeDataBean> lineMapper)
    {
        super();
        setResource(new FileSystemResource("src/main/resources/employee.csv"));
        setName("CSV-Reader");
        setLinesToSkip(1);
        setLineMapper(lineMapper);
        setStrict(false);
    }
}
