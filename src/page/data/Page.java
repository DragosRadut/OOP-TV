package page.data;

import input.data.Actions;
import input.data.Movies;
import input.data.Users;

import java.util.ArrayList;

public interface Page {
    /**
     * change page handler
     * @param whereTo = page to change to
     * @return response as string
     */
    String changePage(String whereTo);

    /**
     * on page action handler
     * @param action = input
     * @param users = all users
     * @param movies = movie list
     * @param currentUser = user
     * @return response from page
     */
    PageResponse action(Actions action, ArrayList<Users> users,
                        ArrayList<Movies> movies, Users currentUser);
}
