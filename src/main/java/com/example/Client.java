package com.example;

import java.util.ArrayList;

public class Client {
    public Map<JobManager> jobs;
    public final String name;

    public Client(String name){
        this.name = name;
        this.jobs = new ArrayList<>();
    }

    public Client(String name, ArrayList<JobManager> jobs){
        this.name = name;
        this.jobs = jobs;
    }

    public ArrayList<JobManager> getJobs(){
        return this.jobs;
    }

    public JobManager getJob(JobManager job){
        return this.jobs.get(job);
    }
}

// TODO: adjust data type of jobs for easy access by object reference
// TODO: update tests for quick changes