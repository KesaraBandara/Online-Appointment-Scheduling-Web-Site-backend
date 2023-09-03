package com.thejobs.onlineappointmentschedulingwebsite.service;

import com.thejobs.onlineappointmentschedulingwebsite.dto.JobDTO;
import com.thejobs.onlineappointmentschedulingwebsite.entity.Job;
import com.thejobs.onlineappointmentschedulingwebsite.repo.JobRepo;
import com.thejobs.onlineappointmentschedulingwebsite.util.VarList;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class JobTypeService {

    @Autowired
    private JobRepo jobRepo;

    @Autowired
    private ModelMapper modelMapper;
    public String saveJobType(JobDTO jobDTO) {

        Job jobEntity = modelMapper.map(jobDTO, Job.class);

        // Check for duplicates based on the time value
        Job existingJob = jobRepo.findByJobType(jobDTO.getJobType());
        System.out.println(jobEntity);
        if (existingJob != null) {
            return VarList.RSP_DUPLICATED;
        }

        // Save the timeEntity
        jobRepo.save(jobEntity);
        return VarList.RSP_SUCCESS;
    }

    public List<JobDTO> getAllJobs() {

        List<Job> jobList = jobRepo.findAll();

        return modelMapper.map(jobList, new TypeToken<List<JobDTO>>() {
        }.getType());

    }
}
