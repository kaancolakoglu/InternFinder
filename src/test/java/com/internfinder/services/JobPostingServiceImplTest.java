package com.internfinder.services;

import com.internfinder.domain.JobPosting;
import com.internfinder.mapper.MapStructMapper;
import com.internfinder.repositories.JobPostingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobPostingServiceImplTest {

    @Mock
    private JobPostingRepository jobPostingRepository;

    @Mock
    private MapStructMapper mapStructMapper;

    @InjectMocks
    private JobPostingServiceImpl jobPostingService;

    private JobPosting jobPosting1;
    private JobPosting jobPosting2;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        // Create test job postings
        jobPosting1 = new JobPosting();
        jobPosting1.setId(1L);
        jobPosting1.setTitle("Java Developer");

        jobPosting2 = new JobPosting();
        jobPosting2.setId(2L);
        jobPosting2.setTitle("Frontend Developer");

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testFindAllJobPostings() {
        // Given
        List<JobPosting> jobPostings = Arrays.asList(jobPosting1, jobPosting2);
        Page<JobPosting> jobPostingPage = new PageImpl<>(jobPostings, pageable, 2);

        when(jobPostingRepository.findAll(pageable)).thenReturn(jobPostingPage);

        // When
        Page<JobPosting> result = jobPostingService.findAllJobPostings(pageable);

        // Then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getContent()).containsExactly(jobPosting1, jobPosting2);
        assertThat(result.getTotalElements()).isEqualTo(2);
    }

    @Test
    void testFindJobPostingById() {
        // Given
        when(jobPostingRepository.findJobPostingById(1L)).thenReturn(Optional.of(jobPosting1));

        // When
        JobPosting result = jobPostingService.findJobPostingById(1L);

        // Then
        assertThat(result).isEqualTo(jobPosting1);
        assertThat(result.getTitle()).isEqualTo("Java Developer");
    }

    @Test
    void testFindJobPostingById_NotFound() {
        // Given
        when(jobPostingRepository.findJobPostingById(999L)).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> jobPostingService.findJobPostingById(999L))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("JobPosting not found");
    }

    @Test
    void testFindJobPostingByCriteria() {
        // Given
        List<JobPosting> jobPostings = Arrays.asList(jobPosting1);
        Page<JobPosting> jobPostingPage = new PageImpl<>(jobPostings, pageable, 1);

        when(jobPostingRepository.findJobPostingsByCriteria(
                eq("Java"), eq(null), eq(null), eq(null),
                eq(null), eq(null), eq(null), eq(null),
                eq(null), eq(null), eq(null), eq(pageable)))
                .thenReturn(jobPostingPage);

        // When
        Page<JobPosting> result = jobPostingService.findJobPostingByCriteria(
                "Java", null, null, null,
                null, null, null, null,
                null, null, null, pageable);

        // Then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("Java Developer");
    }
}