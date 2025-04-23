package com.internfinder.services;

import com.internfinder.domain.JobPosting;
import com.internfinder.repositories.JobPostingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class JobPostingServiceImpl implements JobPostingService {
    private final JobPostingRepository jobPostingRepository;

    public JobPostingServiceImpl(JobPostingRepository jobPostingRepository) {
        this.jobPostingRepository = jobPostingRepository;
    }



    @Override
    public Page<JobPosting> findAllJobPostings(Pageable pageable) {
        log.debug("Retrieving job postings with pagination: {}", pageable);
        return jobPostingRepository.findAll(pageable);
    }
    public JobPosting findJobPostingById(Long id) {
        return jobPostingRepository.findJobPostingById(id)
                .orElseThrow(() -> new NoSuchElementException("JobPosting not found"));
    }

    public Page<JobPosting> findJobPostingByCriteria(String title, String company, String workType, String location,
                                                     LocalDate datePosted, String postingType, Integer minSalary, Integer maxSalary,
                                                     String sourceSite, String experienceLevel, String roleType, Pageable pageable) {

        return jobPostingRepository.findJobPostingsByCriteria(title,company,workType,location,datePosted,postingType,minSalary,
            maxSalary,sourceSite,experienceLevel,roleType,pageable
        );
    }
}
