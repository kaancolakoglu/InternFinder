package com.internfinder.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "location")
public class Location extends BaseClass {

    private String address;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private City city;

    private String postalCode;
    private String latitude;
    private String longitude;

    @OneToMany(mappedBy = "location")
    private Set<JobPosting> jobPostings = new HashSet<>();
}
