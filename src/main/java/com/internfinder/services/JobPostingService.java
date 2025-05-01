package com.internfinder.services;

import com.internfinder.domain.JobPosting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public interface JobPostingService {
    Page<JobPosting> findAllJobPostings(Pageable pageable);

    JobPosting findJobPostingById(Long jobId);

    Page<JobPosting> findJobPostingByCriteria(String title, String company, String workType, String location,
                                              LocalDate datePosted, String postingType, Integer minSalary, Integer maxSalary,
                                              String sourceSite, String experienceLevel, String roleType, Pageable pageable
    );
}
