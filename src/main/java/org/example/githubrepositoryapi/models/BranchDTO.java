package org.example.githubrepositoryapi.models;
public class BranchDTO {
    private String name;
    private String lastCommitSHA;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastCommitSHA() {
        return lastCommitSHA;
    }

    public void setLastCommitSHA(String lastCommitSHA) {
        this.lastCommitSHA = lastCommitSHA;
    }
}
