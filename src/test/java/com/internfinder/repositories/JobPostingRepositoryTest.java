package com.internfinder.repositories;

import com.internfinder.domain.Company;
import com.internfinder.domain.JobPosting;
import com.internfinder.enums.WorkType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JobPostingRepositoryTest {

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private JobPosting jobPosting1;
    private JobPosting jobPosting2;

    @BeforeEach
    void setUp() {
        // Create and save test data
        Company company1 = new Company();
        company1.setName("Tech Corp");

        Company company2 = new Company();
        company2.setName("Web Solutions");

        jobPosting1 = new JobPosting();
        jobPosting1.setTitle("Java Developer");
        jobPosting1.setCompany(company1);
        jobPosting1.setWorkType(WorkType.REMOTE);
        jobPosting1.setMinSalary(50000);
        jobPosting1.setMaxSalary(80000);
        jobPosting1.setDatePosted(LocalDateTime.now().minusDays(5));

        jobPosting2 = new JobPosting();
        jobPosting2.setTitle("Frontend Developer");
        jobPosting2.setCompany(company2);
        jobPosting2.setWorkType(WorkType.HYBRID);
        jobPosting2.setMinSalary(45000);
        jobPosting2.setMaxSalary(70000);
        jobPosting2.setDatePosted(LocalDateTime.now().minusDays(2));

        // Save both to in-memory DB
        jobPostingRepository.save(jobPosting1);
        jobPostingRepository.save(jobPosting2);
        companyRepository.save(company1);
        companyRepository.save(company2);

    }

    @Test
    void testFindById() {
        // When
        Optional<JobPosting> found = jobPostingRepository.findJobPostingById(jobPosting1.getId());

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Java Developer");
    }

    @Test
    void testFindAll() {
        // When
        Pageable pageable = PageRequest.of(0, 10);
        Page<JobPosting> result = jobPostingRepository.findAll(pageable);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(2);
    }

    @Test
    @Disabled
    void testFindByCriteria_Title() {
        // When
        Pageable pageable = PageRequest.of(0, 10);
        Page<JobPosting> result = jobPostingRepository.findJobPostingsByCriteria(
                "Java", null, null, null,
                null, null, null, null,
                null, null, null, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Java Developer");
    }

    @Test
    @Disabled
    void testFindByCriteria_SalaryRange() {
        // When
        Pageable pageable = PageRequest.of(0, 10);
        Page<JobPosting> result = jobPostingRepository.findJobPostingsByCriteria(
                null, null, null, null,
                null, null, 48000, null,
                null, null, null, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1); // Only Java Developer with min 50000
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Java Developer");
    }

    @Test
    @Disabled
    void testFindByCriteria_MultipleFilters() {
        // When
        Pageable pageable = PageRequest.of(0, 10);
        Page<JobPosting> result = jobPostingRepository.findJobPostingsByCriteria(
                null, null, "REMOTE", null,
                null, null, 40000, 90000,
                null, null, null, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().getFirst().getWorkType()).isEqualTo(WorkType.REMOTE);
        assertThat(result.getContent().getFirst().getMinSalary()).isGreaterThanOrEqualTo(40000);
        assertThat(result.getContent().getFirst().getMaxSalary()).isLessThanOrEqualTo(90000);
    }
}