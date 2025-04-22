package com.internfinder.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "currency")
public class Currency extends BaseClass {

    private String name;
    private String code;
    private String symbol;

    @OneToMany(mappedBy = "currency")
    private List<JobPosting> jobPostings;
}
