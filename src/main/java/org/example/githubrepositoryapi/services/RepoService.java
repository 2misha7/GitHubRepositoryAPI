package org.example.githubrepositoryapi.services;
import org.example.githubrepositoryapi.exceptions.UserNotFoundException;
import org.example.githubrepositoryapi.models.AllRepositoriesDTO;
import org.example.githubrepositoryapi.models.BranchDTO;
import org.example.githubrepositoryapi.models.RepositoryDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;


@Service
public class RepoService {
    private final RestTemplate restTemplate = new RestTemplate();
    public AllRepositoriesDTO getAllRepositoriesByUser(String username, String authorizationToken) throws UserNotFoundException{

        AllRepositoriesDTO repositoriesDTO = new AllRepositoriesDTO();
        String url = "https://api.github.com/users/" + username + "/repos";
        
        HttpHeaders headers = new HttpHeaders();
        if(authorizationToken != null){
            headers.set("Authorization", "token " + authorizationToken);
        }

        try {
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> repoResponseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            
            String response =  repoResponseEntity.getBody();
            
            JSONArray allRepoData = new JSONArray(response);
            List<RepositoryDTO> allRepositories = new ArrayList<>();
            //iterating over all repositories to get info about them
            for (int i = 0; i < allRepoData.length(); i++) {
                
                JSONObject oneRepo = allRepoData.getJSONObject(i);
                boolean isFork = oneRepo.getBoolean("fork");
                if (isFork) {
                    continue;
                }
                String name = oneRepo.getString("name");
                String branchesUrl = oneRepo.getString("branches_url").replace("{/branch}", "");

                //getting data about branches of this repo
                ResponseEntity<String> branchesResponseEntity = restTemplate.exchange(branchesUrl, HttpMethod.GET, entity, String.class);
                response = branchesResponseEntity.getBody();

                List<BranchDTO> allBranches = getBranchDTOs(response);

                JSONObject owner = oneRepo.getJSONObject("owner");
                String ownerName = owner.getString("login");


                RepositoryDTO repo = new RepositoryDTO();
                repo.setRepositoryName(name);
                repo.setOwnerLogin(ownerName);
                repo.setBranches(allBranches);
                allRepositories.add(repo);
            }
            repositoriesDTO.setRepositories(allRepositories);
            return repositoriesDTO;
        }catch (HttpClientErrorException.NotFound e){
            throw new UserNotFoundException();
        }
    }

    private static List<BranchDTO> getBranchDTOs(String response) {
        JSONArray branchesArr = new JSONArray(response);
        List<BranchDTO> allBranches = new ArrayList<>();
        for (int j = 0; j < branchesArr.length(); j++) {
            JSONObject oneBranch = branchesArr.getJSONObject(j);
            BranchDTO branchDTO = new BranchDTO();
            branchDTO.setName(oneBranch.getString("name"));
            JSONObject commit = oneBranch.getJSONObject("commit");
            branchDTO.setLastCommitSHA(commit.getString("sha"));
            allBranches.add(branchDTO);
        }
        return allBranches;
    }
}
