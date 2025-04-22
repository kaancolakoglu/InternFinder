package com.internfinder.domain;

import com.internfinder.enums.JobPostingStatus;
import com.internfinder.enums.SourceSite;
import com.internfinder.enums.WorkType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Table(name = "jobPosting")
public class JobPosting extends BaseClass {

    private String title;

    @ManyToOne
    @JoinColumn(name = "company")
    private Company company;

    private WorkType workType;

    @ManyToOne
    @JoinColumn(name = "locationId")
    private Location location;

    private LocalDateTime datePosted;

    @ManyToMany
    @JoinTable(
            name = "postingType",
            joinColumns = @JoinColumn(name = "jobPostingId"),
            inverseJoinColumns = @JoinColumn(name = "postingTypeId")
    )
    private Set<PostingType> postingType = new HashSet<>();

    private Integer minSalary;

    private Integer maxSalary;

    @ManyToOne
    @JoinColumn(name = "currencyId")
    private Currency currency;

    private Boolean salaryIsHourly;

    private SourceSite sourceSite;

    @OneToOne
    @JoinColumn(name = "experienceLevelId")
    private ExperienceLevel ExperienceLevel;

    private JobPostingStatus status;

    @OneToOne
    @JoinColumn(name = "roleType")
    private RoleType roleType;

    private String description;

    private String requirements;

    private String responsibilities;

    private String benefits;

    private String faq;

    private String applicationURL;

    private Integer views;

    private Integer applications;

}
