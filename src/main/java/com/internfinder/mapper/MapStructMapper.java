package com.internfinder.mapper;

import com.internfinder.domain.JobPosting;
import com.internfinder.model.JobPostingDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    JobPostingDTO toDto(JobPosting jobPosting);
}
