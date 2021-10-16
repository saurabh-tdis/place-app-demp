package com.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

/**
 * @author Saurabh Vaish
 * @Date 06-10-2021
 */

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(value="place")
public class Place {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)  // not present in r2dbc
    private Long id;

    private String name;

    private String city ;

    private String state;

    @Column(value="created_date")
    private LocalDate createdDate ;

//    @Enumerated(EnumType.STRING) // not present
    private Status status;

}
