package com.internfinder.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "city")
public class City extends BaseClass {
    private String name;

    @ManyToOne
    @JoinColumn(name = "stateProvinceId")
    private StateProvince stateProvince;

}
