package com.alok.spring.batch.processor;

import com.alok.spring.batch.model.EmployeeDataBean;
import com.alok.spring.batch.model.Student;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class StudentDataReader extends FlatFileItemReader<Student> {

    @Autowired
    public StudentDataReader(LineMapper<Student> lineMapper)
    {
        super();
        setResource(new FileSystemResource("src/main/resources/student.csv"));
        setName("CSV-Reader");
        setLinesToSkip(1);
        setLineMapper(lineMapper);
        setStrict(false);
    }
}
