package PageData;

import InputData.Actions;
import InputData.Users;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginPage implements Page {
    private static String[] navRestrictions = new String[] {"logout", "register"};
    @Override
    public String changePage(String whereTo) {
        if (Arrays.asList(navRestrictions).contains(whereTo)) {
            return whereTo;
        } else {
            return "err";
        }
    }

    @Override
    public String action(Actions action, ArrayList<Users> users) {
        if (!action.getFeature().equals("login"))
            return "err";
        int check = 0;
        for (Users userCheck : users) {
            if (userCheck.getCredentials().getName().equals(action.getCredentials().getName())) {
                if (userCheck.getCredentials().getPassword().equals(action.getCredentials().getPassword())) {
                    check = 1;
                    break;
                }
            }
        }
        if (check == 1)
            return "loginUser";
        return "err";
    }
}
