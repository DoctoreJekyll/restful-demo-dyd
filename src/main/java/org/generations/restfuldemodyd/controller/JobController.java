package org.generations.restfuldemodyd.controller;

import org.generations.restfuldemodyd.dtos.JobDTO;
import org.generations.restfuldemodyd.repository.JobRepository;
import org.generations.restfuldemodyd.service.JobService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobservice;

    public JobController(JobService jobservice) {
        this.jobservice = jobservice;
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> allJobs = jobservice.findAllJobs();
        return new ResponseEntity<>(allJobs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Integer id) {
        JobDTO jobDTO = jobservice.findById(id);
        return new ResponseEntity<>(jobDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JobDTO> saveJob(@RequestBody JobDTO jobDTO) {
        JobDTO saved = jobservice.save(jobDTO);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<JobDTO> updateJob(@RequestBody JobDTO jobDTO) {
        JobDTO updated = jobservice.save(jobDTO);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JobDTO> deleteJob(@PathVariable Integer id) {
        jobservice.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
