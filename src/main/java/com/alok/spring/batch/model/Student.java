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
@Builder
public class Student {
    @Id
    private Integer id;
    private String name;
    private String department;
    private Integer marks;
    private Date time;

}
