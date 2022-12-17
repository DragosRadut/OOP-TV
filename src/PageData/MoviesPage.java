package PageData;

import InputData.Actions;
import InputData.Contains;
import InputData.Movies;
import InputData.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class MoviesPage implements Page {
    private static MoviesPage instance = null;
    public static MoviesPage getInstance() {
        if (instance == null)
            instance = new MoviesPage();
        return instance;
    }
    private static String[] navRestrictions = new String[] {"logout", "see details", "auth", "movies"};
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
        if (action.getFeature().equals("search")) {
            resp.setResponse("setMovies");

            ArrayList<Movies> searched = new ArrayList<Movies>();
            for (Movies movie : movies) {
                if (movie.getName().startsWith(action.getStartsWith())) {
                    Movies aux = new Movies();
                    aux.setName(movie.getName());
                    aux.setActors(movie.getActors());
                    aux.setCountriesBanned(movie.getCountriesBanned());
                    aux.setDuration(movie.getDuration());
                    aux.setGenres(movie.getGenres());
                    aux.setNumLikes(movie.getNumLikes());
                    aux.setNumRatings(movie.getNumRatings());
                    aux.setRating(movie.getRating());
                    aux.setYear(movie.getYear());
                    searched.add(aux);
                }
            }
            resp.setMovies(searched);
            return resp;
        }

        if (action.getFeature().equals("filter")) {
            resp.setResponse("setMovies");
            // create filtered list by contains fields
            ArrayList<Movies> filtered = new ArrayList<Movies>();
            Contains cont = action.getFilters().getContains();
            if (cont != null) {
                for (Movies movie : movies) {
                    filtered.add(movie);
                    int hasActor = 1;
                    // contains actor
                    if (cont.getActors() != null) {
                        for (String actor : cont.getActors()) {
                            if (!movie.getActors().contains(actor)) {
                                filtered.remove(movie);
                                hasActor = 0;
                                break;
                            }
                        }
                    }
                    if (hasActor == 0)
                        continue;
                    // contains genre
                    if (cont.getGenre() != null) {
                        for (String genre : cont.getGenre()) {
                            if (!movie.getGenres().contains(genre)) {
                                filtered.remove(movie);
                                break;
                            }
                        }
                    }
                }
            } else {
                for (Movies movie : movies)
                    filtered.add(movie);
            }
            if (action.getFilters().getSort() != null) {
                // sort filtered list
                String ratingSort = action.getFilters().getSort().getRating();
                String durationSort = action.getFilters().getSort().getDuration();
                if (ratingSort.equals(durationSort)) {
                    if (ratingSort.equals("increasing")) { // both increasing
                        Collections.sort(filtered, new IncRateIncTime());
                    } else { // both decreasing
                        Collections.sort(filtered, new IncRateIncTime().reversed());
                    }
                } else {
                    if (ratingSort.equals("increasing")) { // both increasing
                        Collections.sort(filtered, new DecRateIncTime().reversed());
                    } else { // both decreasing
                        Collections.sort(filtered, new DecRateIncTime());
                    }
                }
            }
            resp.setMovies(filtered);
        }

        return resp;
    }
}

class IncRateIncTime implements Comparator<Movies> {
    @Override
//    public int compare(Movies o1, Movies o2) {
//        if (o1.getRating() == o2.getRating())
//            return o1.getDuration() - o2.getDuration();
//        return ((int) o1.getRating()) - ((int) o2.getRating());
//    }
    public int compare(Movies o1, Movies o2) {
        if (o1.getDuration() == o2.getDuration())
            return ((int) o1.getRating()) - ((int) o2.getRating());
        return o1.getDuration() - o2.getDuration();

    }
}

class DecRateIncTime implements Comparator<Movies> {

    @Override
//    public int compare(Movies o1, Movies o2) {
//        if (o1.getRating() == o2.getRating())
//            return o1.getDuration() - o2.getDuration();
//        return Integer.reverse((int) o1.getRating()) - ((int) o2.getRating());
//    }
    public int compare(Movies o1, Movies o2) {
        if (o1.getDuration() == o2.getDuration())
            return Integer.reverse((int) o1.getRating()) - ((int) o2.getRating());
        return o1.getDuration() - o2.getDuration();

    }
}
