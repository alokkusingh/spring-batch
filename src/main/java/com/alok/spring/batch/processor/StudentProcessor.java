package com.alok.spring.batch.processor;

import com.alok.spring.batch.model.Employee;
import com.alok.spring.batch.model.EmployeeDataBean;
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
    public Student process(Student student) throws Exception {

        String departCode = student.getDepartment();
        String department = DEPARTMENT_MAP.get(departCode);
        student.setDepartment(department);
        student.setTime(new Date());

        if (log.isDebugEnabled())
            log.debug("Converted from [{}] to [{}]", departCode, department);

        return student;
    }
}
