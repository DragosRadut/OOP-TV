package page.data;

import input.data.Actions;
import input.data.Movies;
import input.data.Users;

import java.util.ArrayList;
import java.util.Arrays;

public final class NonAuthPage implements Page {
    private static NonAuthPage instance = null;

    /**
     * Singleton
     * @return instance
     */
    public static NonAuthPage getInstance() {
        if (instance == null) {
            instance = new NonAuthPage();
        }
        return instance;
    }
    private static String[] navRestrictions = new String[] {"login", "register"};
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
        resp.setResponse("err");
        return resp;
    }
}
