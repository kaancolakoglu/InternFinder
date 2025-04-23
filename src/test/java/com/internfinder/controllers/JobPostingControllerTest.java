package com.internfinder.controllers;

import com.internfinder.domain.JobPosting;
import com.internfinder.mapper.MapStructMapper;
import com.internfinder.model.JobPostingDTO;
import com.internfinder.services.JobPostingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JobPostingController.class)
class JobPostingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobPostingService jobPostingService;

    @MockitoBean
    private MapStructMapper mapStructMapper;

    private JobPosting jobPosting1;
    private JobPosting jobPosting2;

    @BeforeEach
    void setUp() {
        // Create test job postings
        jobPosting1 = new JobPosting();
        jobPosting1.setId(1L);
        jobPosting1.setTitle("Java Developer");

        jobPosting2 = new JobPosting();
        jobPosting2.setId(2L);
        jobPosting2.setTitle("Frontend Developer");

        // Create corresponding DTOs
        JobPostingDTO jobPostingDTO1 = new JobPostingDTO();
        jobPostingDTO1.setTitle("Java Developer");

        JobPostingDTO jobPostingDTO2 = new JobPostingDTO();
        jobPostingDTO2.setTitle("Frontend Developer");

        // Configure mapper mock
        when(mapStructMapper.toDto(jobPosting1)).thenReturn(jobPostingDTO1);
        when(mapStructMapper.toDto(jobPosting2)).thenReturn(jobPostingDTO2);
    }

    @Test
    void testListJobPostings() throws Exception {
        // Given
        List<JobPosting> jobPostings = Arrays.asList(jobPosting1, jobPosting2);
        Page<JobPosting> jobPostingPage = new PageImpl<>(jobPostings,
                PageRequest.of(0, 10), 2);

        when(jobPostingService.findAllJobPostings(any(Pageable.class)))
                .thenReturn(jobPostingPage);

        // When/Then
        mockMvc.perform(get("/api/v1/jobPosting")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.content[0].title").value("Java Developer"))
                .andExpect(jsonPath("$.content[1].title").value("Frontend Developer"))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    void testGetJobPostingById() throws Exception {
        // Given
        when(jobPostingService.findJobPostingById(1L)).thenReturn(jobPosting1);

        // When/Then
        mockMvc.perform(get("/api/v1/jobPosting/{jobId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Java Developer"));
    }

    @Test
    void testSearchJobPostings() throws Exception {
        // Given
        List<JobPosting> jobPostings = Arrays.asList(jobPosting1);
        Page<JobPosting> jobPostingPage = new PageImpl<>(jobPostings,
                PageRequest.of(0, 10), 1);

        when(jobPostingService.findJobPostingByCriteria(
                eq("Java"), eq(null), eq(null), eq(null),
                eq(null), eq(null), eq(null), eq(null),
                eq(null), eq(null), eq(null), any(Pageable.class)))
                .thenReturn(jobPostingPage);

        // When/Then
        mockMvc.perform(get("/api/v1/jobPosting/search")
                        .param("title", "Java")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].title").value("Java Developer"));
    }
}