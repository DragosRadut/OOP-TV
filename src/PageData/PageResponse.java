package PageData;

import InputData.Movies;
import InputData.Users;

import java.util.ArrayList;

public class PageResponse {
    private String response;
    private Users user;
    private ArrayList<Movies> movies;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public ArrayList<Movies> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movies> movies) {
        this.movies = movies;
    }
}
