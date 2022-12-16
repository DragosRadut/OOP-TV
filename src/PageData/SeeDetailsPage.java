package PageData;

import InputData.Actions;
import InputData.Movies;
import InputData.Users;

import java.util.ArrayList;
import java.util.Arrays;

public class SeeDetailsPage implements Page {
    private static SeeDetailsPage instance = null;
    public static SeeDetailsPage getInstance() {
        if (instance == null)
            instance = new SeeDetailsPage();
        return instance;
    }
    private static String[] navRestrictions = new String[] {"logout", "auth", "movies", "upgrades"};
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

        if (action.getFeature().equals("purchase")) {
//            if(!action.getMovie().equals(movies.get(0).getName()))
//                return resp;
            Users currentUser = users.get(0);
            if (currentUser.getCredentials().getAccountType().equals("standard")) {
                if (currentUser.getTokensCount() < 2)
                    return resp;
                currentUser.setTokensCount(currentUser.getTokensCount() - 2);
            } else {
                if (currentUser.getNumFreePremiumMovies() < 1)
                    return resp;
                currentUser.setNumFreePremiumMovies(currentUser.getNumFreePremiumMovies() - 1);
            }
            currentUser.setPurchasedMovies(movies);
            resp.setUser(currentUser);
            resp.setResponse("updateUser");
        }
        return resp;
    }
}
