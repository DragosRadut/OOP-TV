import InputData.Credentials;
import InputData.Movies;
import InputData.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public final class Output {
    public ObjectMapper out;
    public Output() {
        this.out = new ObjectMapper();
    }
    public ObjectNode generateOutput(final String error, final ArrayList<Movies> movieList, final Users user) {
        ObjectNode obj = out.createObjectNode();
        obj.put("error", error);

        if (movieList != null) {
            ArrayNode MoviesNode = out.createArrayNode();
            for(Movies movie : movieList) {
                ObjectNode objMovies = out.createObjectNode();
                objMovies.putPOJO("name", movie.getName());
                objMovies.putPOJO("year", movie.getYear());
                objMovies.putPOJO("duration", movie.getDuration());
                objMovies.putPOJO("genres", movie.getGenres());
                objMovies.putPOJO("actors", movie.getActors());
                objMovies.putPOJO("countriesBanned", movie.getCountriesBanned());
                objMovies.putPOJO("numLikes", movie.getNumLikes());
                objMovies.putPOJO("rating", movie.getRating());
                objMovies.putPOJO("numRatings", movie.getNumRatings());
                MoviesNode.add(objMovies);
            }
            obj.putPOJO("currentMoviesList", MoviesNode);
        } else {
            obj.putPOJO("currentMoviesList", new ArrayList<>());
        }

        ObjectNode objUser = out.createObjectNode();
        if (user != null) {
            objUser.putPOJO("credentials", copyCredentials(user));
            objUser.putPOJO("tokensCount", user.getTokensCount());
            objUser.putPOJO("numFreePremiumMovies", user.getNumFreePremiumMovies());
            objUser.putPOJO("purchasedMovies", movieNode(user.getPurchasedMovies()));
            objUser.putPOJO("watchedMovies", movieNode(user.getWatchedMovies()));
            objUser.putPOJO("likedMovies", movieNode(user.getLikedMovies()));
            objUser.putPOJO("ratedMovies", movieNode(user.getRatedMovies()));
        }
        obj.putPOJO("currentUser", objUser);
        return obj;
    }
    public ObjectNode generateError(final String error) {
        ObjectNode obj = out.createObjectNode();
        obj.put("error", error);
        obj.putPOJO("currentMoviesList", new ArrayList<>());
        obj.putPOJO("currentUser", null);
        return obj;
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
    public ArrayNode movieNode(ArrayList<Movies> movieList) {
        ArrayNode MoviesNode = out.createArrayNode();
        if (movieList != null) {
            for(Movies movie : movieList) {
                ObjectNode objMovies = out.createObjectNode();
                objMovies.putPOJO("name", movie.getName());
                objMovies.putPOJO("year", movie.getYear());
                objMovies.putPOJO("duration", movie.getDuration());
                objMovies.putPOJO("genres", movie.getGenres());
                objMovies.putPOJO("actors", movie.getActors());
                objMovies.putPOJO("countriesBanned", movie.getCountriesBanned());
                objMovies.putPOJO("numLikes", movie.getNumLikes());
                objMovies.putPOJO("rating", movie.getRating());
                objMovies.putPOJO("numRatings", movie.getNumRatings());
                MoviesNode.add(objMovies);
            }
        }
        return MoviesNode;
    }
}
