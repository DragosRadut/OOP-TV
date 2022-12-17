package page.data;

import input.data.Movies;
import input.data.Users;

import java.util.ArrayList;

public final class PageResponse {
    private String response;
    private Users user;
    private ArrayList<Movies> movies;

    public String getResponse() {
        return response;
    }

    public void setResponse(final String response) {
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

    public void setMovies(final ArrayList<Movies> movies) {
        this.movies = movies;
    }
}
