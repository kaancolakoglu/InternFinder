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
@Table(name = "stateProvince")
public class StateProvince extends BaseClass {

    @ManyToOne
    private Country country;

    @OneToMany(mappedBy = "stateProvince")
    private Set<City> cities = new HashSet<>();
}
