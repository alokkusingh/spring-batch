package com.alok.spring.batch.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
    @Id
    private Integer id;
    private String name;
    private String department;
    private String salary;
    private Date time;

}
