package PageData;

import InputData.Actions;
import InputData.Users;

import java.util.ArrayList;
import java.util.Arrays;

public class NonAuthPage implements Page {
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
    public String action(Actions action, ArrayList<Users> users) {
        return "err";
    }
}
