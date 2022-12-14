package PageData;

import InputData.Actions;
import InputData.Movies;
import InputData.Users;

import java.util.ArrayList;
import java.util.Arrays;

public class RegisterPage implements Page {
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
    public String action(Actions action, ArrayList<Users> users) {
        int check = 0;
//        for (Users userCheck : users) {
//            if (userCheck.getCredentials().getName().equals(action.getCredentials().getName())) {
//                if (userCheck.getCredentials().getPassword().equals(action.getCredentials().getPassword())) {
//                    check = 1;
//                    break;
//                }
//            }
//        }
        if (check == 1)
            return "err";
        Users newUser = new Users();
        newUser.setCredentials(action.getCredentials());
        newUser.setLikedMovies(new ArrayList<Movies>());
        newUser.setPurchasedMovies(new ArrayList<Movies>());
        newUser.setRatedMovies(new ArrayList<Movies>());
        newUser.setWatchedMovies(new ArrayList<Movies>());
        newUser.setTokensCount(0);
        newUser.setNumFreePremiumMovies(15);
        users.add(newUser);
        return "registerUser";
    }
}
