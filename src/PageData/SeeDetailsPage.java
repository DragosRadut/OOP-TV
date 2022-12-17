package PageData;

import InputData.Actions;
import InputData.Credentials;
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
    private static String[] navRestrictions = new String[] {"logout", "auth", "movies", "upgrades", "see details"};
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
        if (action.getFeature().equals("purchase")) {
            if(action.getMovie() != null && !action.getMovie().equals(movies.get(0).getName()))
                return resp;
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
            resp.setResponse("updateUserMovies");
            return resp;
        }

        if (action.getFeature().equals("watch")) {
            ArrayList<Movies> addMovies = new ArrayList<Movies>();
            for (Movies purchased : currentUser.getPurchasedMovies()) { // check is purchased
                if (purchased.getName().equals(action.getMovie())) {
                    addMovies.add(purchased);
                    currentUser.setWatchedMovies(addMovies);
                    resp.setResponse("updateUserMovies");
                    resp.setUser(currentUser);
                    break;
                }
            }
            return resp;
        }
        if (action.getFeature().equals("like")) {
            ArrayList<Movies> addMovies = new ArrayList<Movies>();
            for (Movies watched : currentUser.getWatchedMovies()) { // check is watched
                if (watched.getName().equals(action.getMovie())) {
                    watched.setNumLikes(watched.getNumLikes() + 1);
                    addMovies.add(watched);
                    currentUser.getLikedMovies().add(watched);
                    resp.setResponse("updateUserMovies");
                    resp.setUser(currentUser);
                    break;
                }
            }
        }
        if (action.getFeature().equals("rate")) {
            if (action.getRate() < 1 || action.getRate() > 5)
                return resp;
            ArrayList<Movies> addMovies = new ArrayList<Movies>();
            for (Movies rated : currentUser.getWatchedMovies()) { // check is watched
                if (rated.getName().equals(action.getMovie())) {
                    rated.setRating(rated.getRating() + action.getRate());
                    rated.setNumRatings(rated.getNumRatings() + 1);
                    addMovies.add(rated);
                    currentUser.getRatedMovies().add(rated);
                    resp.setResponse("updateUserMovies");
                    resp.setUser(currentUser);
                    break;
                }
            }
            return resp;
        }
        return resp;
    }
}
