package com.example;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class Client {
    public HashSet<JobManager> jobs;
    public final String name;

    public Client(String name){
        this.name = name;
        this.jobs = new HashSet<>();
    }

    public Client(String name, HashSet<JobManager> jobs){
        this.name = name;
        this.jobs = jobs;
    }

    public HashSet<JobManager> getJobs(){
        return this.jobs;
    }

    public JobManager getJob(JobManager job){
        for (JobManager job_manager : this.jobs){
            if (job == job_manager){return job_manager;}
        }
        return null;
    }

    public void deleteJob(JobManager job){
        for (JobManager job_manager : this.jobs){
            if (job == job_manager){this.jobs.remove(job);}
        }
        throw new NoSuchElementException("Did not find job to delete.");
    }
}

// TODO: adjust data type of jobs for easy access by object reference
// TODO: update tests for quick changes