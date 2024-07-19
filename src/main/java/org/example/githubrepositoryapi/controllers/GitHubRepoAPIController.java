package org.example.githubrepositoryapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.githubrepositoryapi.exceptions.UserNotFoundException;
import org.example.githubrepositoryapi.models.AllRepositoriesDTO;
import org.example.githubrepositoryapi.services.RepoService;
import org.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping(path="/api/github/users",
        produces = {MediaType.APPLICATION_JSON_VALUE})
public class GitHubRepoAPIController {
    private final RepoService repoService;

    public GitHubRepoAPIController(RepoService repoService) {
        this.repoService = repoService;
    }

    @Tag(name = "GET", description = "Get all his github repositories of the user, which are not forks.")
    @GetMapping("repositories")
    public ResponseEntity<?> getRepositories(@RequestParam String username, @RequestHeader("Accept") String acceptHeader) {
        //if (!"application/json".equals(acceptHeader)) {
        //    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
        //            .body(Map.of("status", HttpStatus.NOT_ACCEPTABLE.value(),
        //                    "message", "Accept header must be application/json"));
        //}

        try {
            AllRepositoriesDTO repositoriesDTO = repoService.getAllRepositoriesByUser(username);
            return ResponseEntity.ok(repositoriesDTO);
        }catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

}
