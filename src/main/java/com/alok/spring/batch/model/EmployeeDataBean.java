package com.alok.spring.batch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeDataBean {
    private Integer id;
    private String name;
    private String department;
    private String salary;
}
