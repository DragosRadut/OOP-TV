package page.data;

import input.data.Actions;
import input.data.Movies;
import input.data.Users;

import java.util.ArrayList;
import java.util.Arrays;

public final class LoginPage implements Page {
    private static LoginPage instance = null;

    /**
     * Singleton
     * @return instance
     */
    public static LoginPage getInstance() {
        if (instance == null) {
            instance = new LoginPage();
        }
        return instance;
    }
    private static String[] navRestrictions = new String[] {"logout", "register", "login"};
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
        if (!action.getFeature().equals("login")) {
            resp.setResponse("err");
            return resp;
        }
        int check = 0;
        for (Users userCheck : users) {
            if (userCheck.getCredentials().getName().equals(action.getCredentials().getName())) {
                if (userCheck.getCredentials().getPassword().equals(action.getCredentials().getPassword())) {
                    check = 1;
                    resp.setResponse("loginUser");
                    resp.setUser(userCheck);
                    break;
                }
            }
        }
        if (check != 1) {
            resp.setResponse("err");
        }
        return resp;
    }
}
