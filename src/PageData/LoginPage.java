package PageData;

import InputData.Actions;
import InputData.Movies;
import InputData.Users;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginPage implements Page {
    private static LoginPage instance = null;
    public static LoginPage getInstance() {
        if (instance == null)
            instance = new LoginPage();
        return instance;
    }
    private static String[] navRestrictions = new String[] {"logout", "register", "login"};
    @Override
    public String changePage(String whereTo) {
        if (Arrays.asList(navRestrictions).contains(whereTo)) {
            return whereTo;
        } else {
            return "err";
        }
    }

    @Override
    public PageResponse action(Actions action, ArrayList<Users> users, ArrayList<Movies> movies, Users currentUser) {
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
