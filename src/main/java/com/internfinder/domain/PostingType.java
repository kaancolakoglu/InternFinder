package com.internfinder.domain;

import jakarta.annotation.Nonnull;
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
@Table(name = "postingType")
public class PostingType extends BaseClass {
    private String name;
    private String description;

    @ManyToMany(mappedBy = "postingType")
    private Set<JobPosting> jobPostings = new HashSet<JobPosting>();

}
