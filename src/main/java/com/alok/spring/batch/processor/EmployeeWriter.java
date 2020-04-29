package com.alok.spring.batch.processor;

import com.alok.spring.batch.model.Employee;
import com.alok.spring.batch.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class EmployeeWriter implements ItemWriter<Employee> {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void write(List<? extends Employee> employees) throws Exception {

        if (log.isDebugEnabled())
            log.debug("Persisting data for employees: {}", employees);

        employeeRepository.saveAll(employees);
    }
}
