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
            objUser.putPOJO("credentials", user.getCredentials());
            objUser.putPOJO("tokensCount", user.getTokensCount());
            objUser.putPOJO("numFreePremiumMovies", user.getNumFreePremiumMovies());
            objUser.putPOJO("purchasedMovies", user.getPurchasedMovies());
            objUser.putPOJO("watchedMovies", user.getWatchedMovies());
            objUser.putPOJO("likedMovies", user.getLikedMovies());
            objUser.putPOJO("ratedMovies", user.getRatedMovies());
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
}
