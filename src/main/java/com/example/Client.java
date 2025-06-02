package com.example;
import java.util.HashSet;
import java.util.NoSuchElementException;

public class Client {
    private final HashSet<AccountsManager> jobs;
    public final String name;

    public Client(String name){
        this.name = name;
        this.jobs = new HashSet<>();
    }

    public Client(String name, HashSet<AccountsManager> jobs){
        this.name = name;
        this.jobs = jobs;
    }

    public void newJob(AccountsManager job){
        if (this.jobs.contains(job)){
            throw new IllegalArgumentException("Job already exists in Client. Change attribute(s) of Job.");
        }
        this.jobs.add(job);
    }

    public HashSet<AccountsManager> getJobs(){
        return this.jobs;
    }

    public AccountsManager getJob(AccountsManager job){
        for (AccountsManager job_manager : this.jobs){
            if (job == job_manager){return job_manager;}
        }
        return null;
    }

    public void deleteJob(AccountsManager job){
        for (AccountsManager job_manager : this.jobs){
            if (job == job_manager){this.jobs.remove(job);}
        }
        throw new NoSuchElementException("Did not find job to delete.");
    }
}
