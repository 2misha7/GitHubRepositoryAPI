package org.example.githubrepositoryapi.controllers;
import org.example.githubrepositoryapi.exceptions.UserNotFoundException;
import org.example.githubrepositoryapi.models.AllRepositoriesDTO;
import org.example.githubrepositoryapi.models.UserNotFoundDTO;
import org.example.githubrepositoryapi.services.RepoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/github/users",
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class GitHubRepoAPIController {
    private final RepoService repoService;

    public GitHubRepoAPIController(RepoService repoService) {
        this.repoService = repoService;
    }

    @GetMapping("repositories")
    public ResponseEntity<?> getRepositories(@RequestParam String username, @RequestParam(required = false) String authorizationToken) {
        try {
            AllRepositoriesDTO repositoriesDTO = repoService.getAllRepositoriesByUser(username, authorizationToken);
            return ResponseEntity.ok(repositoriesDTO);
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UserNotFoundDTO(username));
        }
    }

}
