package com.internfinder.controllers;

import com.internfinder.domain.JobPosting;
import com.internfinder.mapper.MapStructMapper;
import com.internfinder.model.JobPostingDTO;
import com.internfinder.services.JobPostingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class JobPostingController {
    private final JobPostingService jobPostingService;
    private final MapStructMapper mapStructMapper;

    public JobPostingController(JobPostingService jobPostingService, MapStructMapper mapStructMapper) {
        this.jobPostingService = jobPostingService;
        this.mapStructMapper = mapStructMapper;
    }

    @GetMapping("/jobPosting")
    public ResponseEntity<Page<JobPostingDTO>> listJobPostings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<JobPosting> jobPostingsPage = jobPostingService.findAllJobPostings(pageable);

        Page<JobPostingDTO> dtoPage = jobPostingsPage.map(mapStructMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("jobPosting/{jobId}")
    public ResponseEntity<JobPostingDTO> getJobPostingByJobId(@PathVariable Long jobId) {
        JobPostingDTO jobPosting = mapStructMapper.toDto(jobPostingService.findJobPostingById(jobId));

        return ResponseEntity.ok(jobPosting);
    }

    @GetMapping("/jobPosting/search")
    public ResponseEntity<Page<JobPostingDTO>> searchJobPostings(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String company,
            @RequestParam(required = false) String workType,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) LocalDate datePosted,
            @RequestParam(required = false) String postingType,
            @RequestParam(required = false) Integer minSalary,
            @RequestParam(required = false) Integer maxSalary,
            @RequestParam(required = false) String sourceSite,
            @RequestParam(required = false) String experienceLevel,
            @RequestParam(required = false) String roleType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));

        Page<JobPosting> filteredJobPosting =
                jobPostingService.findJobPostingByCriteria(title,company,workType,location,datePosted,
                        postingType,minSalary,maxSalary,sourceSite,experienceLevel,roleType, pageable);

        Page<JobPostingDTO> dtoPage = filteredJobPosting.map(mapStructMapper::toDto);

        return ResponseEntity.ok(dtoPage);
    }
}
