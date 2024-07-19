# GitHubRepositoryAPI

This project provides a RESTful API to fetch information about GitHub repositories,which are not forks for a specified user, including details about branches and their latest commits.

## Table of Contents

- [Usage](#usage)
- [API Endpoints](#api-endpoints)

## Usage

The application will start on `http://localhost:8080`. You can interact with the API using tools like `curl`, Postman, or your web browser.

## API Endpoints

### Get Repositories

- **URL:** `/api/github/users/repositories`
- **Method:** `GET`
- **Query Parameters:**
  - `username` (required): GitHub username to fetch repositories for.
  - `authorizationToken` (optional): GitHub personal access token for authentication.
- **Responses:**
  - `200 OK`: Returns a list of repositories and their branches.
  - `404 Not Found`: If the specified user is not found.

#### Example Request

```sh
curl -X GET "http://localhost:8080/api/github/users/repositories?username=user123"
