package com.alok.spring.batch.processor;

import com.alok.spring.batch.model.Student;
import com.alok.spring.batch.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class StudentWriter implements ItemWriter<Student> {

    @Autowired
    StudentRepository studentRepository;

    @Override
    public void write(List<? extends Student> students) throws Exception {

        if (log.isDebugEnabled())
            log.debug("Persisting data for employees: {}", students);

        studentRepository.saveAll(students);
    }
}
