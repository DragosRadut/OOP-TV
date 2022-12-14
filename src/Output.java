import InputData.Movies;
import InputData.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class Output {
    public ObjectMapper out;
    public Output() {
        this.out = new ObjectMapper();
    }
    public ObjectNode generate(final String error, final Movies movieList, final Users user) {
        ObjectNode obj = out.createObjectNode();
        obj.put("error", error);

        ObjectNode objMovies = out.createObjectNode();
        //objMovies.putPOJO()
        obj.putPOJO("currentMoviesList", objMovies);

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
}
