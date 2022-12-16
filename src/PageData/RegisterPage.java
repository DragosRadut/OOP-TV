package PageData;

import InputData.Actions;
import InputData.Movies;
import InputData.Users;

import java.util.ArrayList;
import java.util.Arrays;

public class RegisterPage implements Page {
    private static RegisterPage instance = null;
    public static RegisterPage getInstance() {
        if (instance == null)
            instance = new RegisterPage();
        return instance;
    }

    private static String[] navRestrictions = new String[] {"logout", "login"};
    @Override
    public String changePage(String whereTo) {
        if (Arrays.asList(navRestrictions).contains(whereTo)) {
            return whereTo;
        } else {
            return "err";
        }
    }

    @Override
    public PageResponse action(Actions action, ArrayList<Users> users, ArrayList<Movies> movies) {
        PageResponse resp = new PageResponse();
        resp.setResponse("registerUser");

        Users newUser = new Users();
        newUser.setCredentials(action.getCredentials());
        users.add(newUser);

        resp.setUser(newUser);
        return resp;
    }
}
