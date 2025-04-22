package com.internfinder.domain;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "company")
public class Company extends BaseClass{
    private String name;
    private String description;
    private String mission;
    private String website;
    private String logoURL;
    private Integer foundedYear;

    @OneToOne
    @JoinColumn(name = "companySizeId")
    private CompanySize companySize;

    @OneToOne
    @JoinColumn(name = "industryId")
    private Industry industry;

}
