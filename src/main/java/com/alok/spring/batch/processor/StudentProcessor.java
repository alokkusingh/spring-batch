package com.alok.spring.batch.processor;

import com.alok.spring.batch.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class StudentProcessor implements ItemProcessor<Student, Student> {

    private static final Map<String, String > DEPARTMENT_MAP = new HashMap<>();

    public StudentProcessor() {
        DEPARTMENT_MAP.put("001", "Engineering");
        DEPARTMENT_MAP.put("002", "Sustaining");
        DEPARTMENT_MAP.put("003", "Profession Service");
    }

    @Override
    public Student process(Student student) {

        return Student.builder()
                .id(student.getId())
                .name(student.getName())
                .department(DEPARTMENT_MAP.get(student.getDepartment()))
                .marks(student.getMarks())
                .time(new Date())
                .build();
    }
}
