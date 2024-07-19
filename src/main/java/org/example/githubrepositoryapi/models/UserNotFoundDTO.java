package org.example.githubrepositoryapi.models;
import org.springframework.http.HttpStatus;

public class UserNotFoundDTO {
    private int status;
    private String message;
    public UserNotFoundDTO(String username){
        this.message = "Github user with username '" + username + "' doesn't exist";
        this.status = HttpStatus.NOT_FOUND.value();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
