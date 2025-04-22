package com.internfinder.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "country")
public class Country extends BaseClass{

    private String name;

    @OneToMany(mappedBy = "country")
    private Set<StateProvince> stateProvinces = new HashSet<StateProvince>();
}
