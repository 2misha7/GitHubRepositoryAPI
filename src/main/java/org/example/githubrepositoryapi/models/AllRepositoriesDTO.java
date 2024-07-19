package org.example.githubrepositoryapi.models;

import java.util.List;

public class AllRepositoriesDTO {
    private List<RepositoryDTO> repositories;


    public List<RepositoryDTO> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<RepositoryDTO> repositories) {
        this.repositories = repositories;
    }
}
