package PageData;

import InputData.Actions;
import InputData.Credentials;
import InputData.Movies;
import InputData.Users;

import java.util.ArrayList;
import java.util.Arrays;

public class UpgradesPage implements Page {
    private static UpgradesPage instance = null;
    public static UpgradesPage getInstance() {
        if (instance == null)
            instance = new UpgradesPage();
        return instance;
    }
    private static String[] navRestrictions = new String[] {"auth", "movies", "logout"};
    @Override
    public String changePage(final String whereTo) {
        if (Arrays.asList(navRestrictions).contains(whereTo)) {
            return whereTo;
        } else {
            return "err";
        }
    }

    @Override
    public PageResponse action(Actions action, ArrayList<Users> users, ArrayList<Movies> movies, Users currentUser) {
        PageResponse resp = new PageResponse();
        resp.setResponse("err");

        if (action.getFeature().equals("buy tokens")) {
            int nrCount = Integer.parseInt(action.getCount());
            if (Integer.parseInt(currentUser.getCredentials().getBalance()) < nrCount)
                return resp;
            Credentials newCreds = copyCredentials(currentUser);
            newCreds.setBalance(String.valueOf((Integer.parseInt(newCreds.getBalance()) - nrCount)));
            currentUser.setCredentials(newCreds);
            currentUser.setTokensCount(currentUser.getTokensCount() + nrCount);
            resp.setUser(currentUser);
            resp.setResponse("updateUser");
            return resp;
        }

        if (action.getFeature().equals("buy premium account")) {
            if (currentUser.getTokensCount() < 10 || currentUser.getCredentials().getAccountType().equals("premium"))
                return resp;
            currentUser.setTokensCount(currentUser.getTokensCount() - 10);
            Credentials newCreds = copyCredentials(currentUser);
            newCreds.setAccountType("premium");
            currentUser.setCredentials(newCreds);
            resp.setUser(currentUser);
            resp.setResponse("updateUser");
            return resp;
        }
        return resp;
    }

    public Credentials copyCredentials(Users currentUser) {
        Credentials newCreds = new Credentials();
        newCreds.setBalance(currentUser.getCredentials().getBalance());
        newCreds.setAccountType(currentUser.getCredentials().getAccountType());
        newCreds.setName(currentUser.getCredentials().getName());
        newCreds.setCountry(currentUser.getCredentials().getCountry());
        newCreds.setPassword(currentUser.getCredentials().getPassword());
        return newCreds;
    }
}
