package com.alok.spring.batch.processor;

import com.alok.spring.batch.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

    private static final Map<String, String > DEPARTMENT_MAP = new HashMap<>();

    public EmployeeProcessor() {
        DEPARTMENT_MAP.put("001", "Engineering");
        DEPARTMENT_MAP.put("002", "Sustaining");
        DEPARTMENT_MAP.put("003", "Profession Service");
    }

    @Override
    public Employee process(Employee employee) throws Exception {

        String departCode = employee.getDepartment();
        String department = DEPARTMENT_MAP.get(departCode);
        employee.setDepartment(department);
        employee.setTime(new Date());

        if (log.isDebugEnabled())
            log.debug("Converted from [{}] to [{}]", departCode, department);

        return employee;
    }
}
