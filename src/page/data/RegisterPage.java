package page.data;

import input.data.Actions;
import input.data.Movies;
import input.data.Users;

import java.util.ArrayList;
import java.util.Arrays;

public final class RegisterPage implements Page {
    private static RegisterPage instance = null;

    /**
     * Singleton
     * @return instance
     */
    public static RegisterPage getInstance() {
        if (instance == null) {
            instance = new RegisterPage();
        }
        return instance;
    }

    private static String[] navRestrictions = new String[] {"logout", "login"};
    @Override
    public String changePage(final String whereTo) {
        if (Arrays.asList(navRestrictions).contains(whereTo)) {
            return whereTo;
        } else {
            return "err";
        }
    }

    @Override
    public PageResponse action(final Actions action, final ArrayList<Users> users,
                               final ArrayList<Movies> movies, final Users currentUser) {
        PageResponse resp = new PageResponse();
        resp.setResponse("registerUser");

        Users newUser = new Users();
        newUser.setCredentials(action.getCredentials());
        users.add(newUser);

        resp.setUser(newUser);
        return resp;
    }
}
