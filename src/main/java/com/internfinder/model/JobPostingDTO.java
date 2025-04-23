package com.internfinder.model;

import com.internfinder.domain.*;
import com.internfinder.enums.JobPostingStatus;
import com.internfinder.enums.SourceSite;
import com.internfinder.enums.WorkType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobPostingDTO {

    private String title;
    private Company company;
    private WorkType workType;
    private Location location;
    private LocalDateTime datePosted;
    private Set<PostingType> postingType = new HashSet<>();
    private Integer minSalary;
    private Integer maxSalary;
    private Currency currency;
    private Boolean salaryIsHourly;
    private SourceSite sourceSite;
    private ExperienceLevel experienceLevel;
    private JobPostingStatus status;
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
