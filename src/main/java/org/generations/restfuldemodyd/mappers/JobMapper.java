package org.generations.restfuldemodyd.mappers;

import org.generations.restfuldemodyd.dtos.JobDTO;
import org.generations.restfuldemodyd.model.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper{

    public JobDTO mapToDTO(Job job){
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setName(job.getName());
        jobDTO.setDescription(job.getDescription());
        return jobDTO;
    }

    public Job mapToEntity(JobDTO jobDTO){
        Job job = new Job();
        job.setId(jobDTO.getId());
        job.setName(jobDTO.getName());
        job.setDescription(jobDTO.getDescription());
        return job;
    }
}
