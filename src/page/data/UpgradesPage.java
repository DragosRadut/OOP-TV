package page.data;

import input.data.Actions;
import input.data.Credentials;
import input.data.Movies;
import input.data.Users;

import java.util.ArrayList;
import java.util.Arrays;

public final class UpgradesPage implements Page {
    private static int cost = 10;
    private static UpgradesPage instance = null;

    /**
     * Singleton
     * @return instance
     */
    public static UpgradesPage getInstance() {
        if (instance == null) {
            instance = new UpgradesPage();
        }
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
    public PageResponse action(final Actions action, final ArrayList<Users> users,
                               final ArrayList<Movies> movies, final Users currentUser) {
        PageResponse resp = new PageResponse();
        resp.setResponse("err");

        if (action.getFeature().equals("buy tokens")) {
            int nrCount = Integer.parseInt(action.getCount());
            if (Integer.parseInt(currentUser.getCredentials().getBalance()) < nrCount) {
                return resp;
            }
            Credentials newCreds = copyCredentials(currentUser);
            newCreds.setBalance(String.valueOf((Integer.parseInt(newCreds.getBalance())
                    - nrCount)));
            currentUser.setCredentials(newCreds);
            currentUser.setTokensCount(currentUser.getTokensCount() + nrCount);
            resp.setUser(currentUser);
            resp.setResponse("updateUser");
            return resp;
        }

        if (action.getFeature().equals("buy premium account")) {
            if (currentUser.getTokensCount() < cost
                    || currentUser.getCredentials().getAccountType().equals("premium")) {
                return resp;
            }
            currentUser.setTokensCount(currentUser.getTokensCount() - cost);
            Credentials newCreds = copyCredentials(currentUser);
            newCreds.setAccountType("premium");
            currentUser.setCredentials(newCreds);
            resp.setUser(currentUser);
            resp.setResponse("updateUser");
            return resp;
        }
        return resp;
    }

    /**
     * auxiliary method
     * @param currentUser = user
     * @return credentials
     */
    public Credentials copyCredentials(final Users currentUser) {
        Credentials newCreds = new Credentials();
        newCreds.setBalance(currentUser.getCredentials().getBalance());
        newCreds.setAccountType(currentUser.getCredentials().getAccountType());
        newCreds.setName(currentUser.getCredentials().getName());
        newCreds.setCountry(currentUser.getCredentials().getCountry());
        newCreds.setPassword(currentUser.getCredentials().getPassword());
        return newCreds;
    }
}
