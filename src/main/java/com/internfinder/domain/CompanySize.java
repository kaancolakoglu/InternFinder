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
@Table(name = "companySize")
public class CompanySize extends BaseClass{

    private String name;

    private String description;

}
