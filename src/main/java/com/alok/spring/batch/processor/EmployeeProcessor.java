package com.alok.spring.batch.processor;

import com.alok.spring.batch.model.Employee;
import com.alok.spring.batch.model.EmployeeDataBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class EmployeeProcessor implements ItemProcessor<EmployeeDataBean, Employee> {

    private static final Map<String, String > DEPARTMENT_MAP = new HashMap<>();

    public EmployeeProcessor() {
        DEPARTMENT_MAP.put("001", "Engineering");
        DEPARTMENT_MAP.put("002", "Sustaining");
        DEPARTMENT_MAP.put("003", "Profession Service");
    }

    @Override
    public Employee process(EmployeeDataBean employeeData) throws Exception {

        Employee employee = Employee.builder()
                .id(employeeData.getId())
                .name(employeeData.getName())
                .salary(employeeData.getSalary())
                .departmentCode(employeeData.getDepartment())
                .department(DEPARTMENT_MAP.get(employeeData.getDepartment()))
                .time(new Date())
                .build();

        if (log.isDebugEnabled())
            log.debug("Converted from [{}] to [{}]", employeeData.getDepartment(), employee.getDepartment());

        return employee;
    }
}
