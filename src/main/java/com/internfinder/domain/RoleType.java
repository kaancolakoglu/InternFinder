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
@Table(name = "roleType")
public class RoleType extends BaseClass{

    private String name;
    private String description;
}
