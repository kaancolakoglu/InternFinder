package com.internfinder.domain;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.antlr.v4.runtime.misc.NotNull;

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
