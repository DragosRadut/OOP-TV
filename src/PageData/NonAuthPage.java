package PageData;

import InputData.Actions;
import InputData.Movies;
import InputData.Users;

import java.util.ArrayList;
import java.util.Arrays;

public class NonAuthPage implements Page {
    private static NonAuthPage instance = null;
    public static NonAuthPage getInstance() {
        if (instance == null)
            instance = new NonAuthPage();
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
    public PageResponse action(Actions action, ArrayList<Users> users, ArrayList<Movies> movies) {
        PageResponse resp = new PageResponse();
        resp.setResponse("err");
        return resp;
    }
}
