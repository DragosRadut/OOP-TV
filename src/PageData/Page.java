package PageData;

import InputData.Actions;
import InputData.Movies;
import InputData.Users;

import java.util.ArrayList;

public interface Page {
    public String changePage(final String whereTo);
    public PageResponse action(final Actions action, ArrayList<Users> users, ArrayList<Movies> movies);
}
