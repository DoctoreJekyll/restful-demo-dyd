package org.generations.restfuldemodyd.service;

import org.generations.restfuldemodyd.dtos.JobDTO;
import org.generations.restfuldemodyd.errors.ResourceNotFoundException;
import org.generations.restfuldemodyd.mappers.JobMapper;
import org.generations.restfuldemodyd.model.Job;
import org.generations.restfuldemodyd.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    public JobService(JobRepository jobRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.jobMapper = jobMapper;
    }


    public List<JobDTO> findAllJobs() {
        return jobRepository.findAll()
                .stream()
                .map(jobMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public JobDTO findById(Integer id) {
        return jobMapper.
                mapToDTO(jobRepository.findById(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Job not found with id " + id)));
    }

    public JobDTO save(JobDTO jobDTO) {
        return jobMapper.mapToDTO(jobRepository.save(jobMapper.mapToEntity(jobDTO)));
    }

    public void deleteById(Integer id) {
        jobRepository.deleteById(id);
    }



}
