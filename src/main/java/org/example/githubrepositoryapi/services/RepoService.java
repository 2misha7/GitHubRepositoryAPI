package org.example.githubrepositoryapi.services;
import org.example.githubrepositoryapi.exceptions.UserNotFoundException;
import org.example.githubrepositoryapi.models.AllRepositoriesDTO;
import org.example.githubrepositoryapi.models.BranchDTO;
import org.example.githubrepositoryapi.models.RepositoryDTO;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class RepoService {
    private final RestTemplate restTemplate = new RestTemplate();
    public AllRepositoriesDTO getAllRepositoriesByUser(String username) throws UserNotFoundException, IOException, JSONException {



        AllRepositoriesDTO repositoriesDTO = new AllRepositoriesDTO();
        String url = "https://api.github.com/users/" + username + "/repos";
        String response = restTemplate.getForObject(url, String.class);

        JSONArray arr = new JSONArray(response);
        //System.out.println(arr);

        List<RepositoryDTO> allRepositories = new ArrayList<>();
        for(int i = 0 ; i < arr.length(); i++)
        {
            List<BranchDTO> allBranches = new ArrayList<>();
            JSONObject a = arr.getJSONObject(i);
            System.out.println(a.getBoolean("fork"));
            String name = a.getString("name");


            String branchesUrl = a.getString("branches_url").replace("{/branch}", "");
            response = restTemplate.getForObject(branchesUrl, String.class);

            JSONArray branchesArr = new JSONArray(response);
            for(int j = 0; j < branchesArr.length(); j++){
                JSONObject oneBranch = branchesArr.getJSONObject(j);
                BranchDTO branchDTO = new BranchDTO();
                branchDTO.setName(oneBranch.getString("name"));
                JSONObject commit = oneBranch.getJSONObject("commit");
                branchDTO.setLastCommitSHA(commit.getString("sha"));
                allBranches.add(branchDTO);
            }



            JSONObject owner = a.getJSONObject("owner");

            String ownerName = owner.getString("login");

            RepositoryDTO r = new RepositoryDTO();
            r.setRepositoryName(name);
            r.setOwnerLogin(ownerName);
            r.setBranches(allBranches);

            allRepositories.add(r);
        }

        repositoriesDTO.setRepositories(allRepositories);
        return repositoriesDTO;
        //throw new UserNotFoundException();
    }
}
