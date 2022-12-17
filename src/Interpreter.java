import InputData.Actions;
import InputData.Movies;
import InputData.Users;
import PageData.*;

import java.util.ArrayList;

public class Interpreter {
    private String currentPage;
    private Users currentUser;
    private ArrayList<Movies> currentMovies;
    private Movies lastSearched;
    public String exec(final Actions action, ArrayList<Users> users, ArrayList<Movies> moviesList) {
        // determine current page type
        Page page = null;
        switch (currentPage) {
            case "logout":
                page = NonAuthPage.getInstance();
                break;
            case "login":
                page = LoginPage.getInstance();
                break;
            case "register":
                page = RegisterPage.getInstance();
                break;
            case "auth":
                page = AuthPage.getInstance();
                break;
            case "movies":
                page = MoviesPage.getInstance();
                break;
            case "see details":
                page = SeeDetailsPage.getInstance();
                break;
            case "upgrades":
                page = UpgradesPage.getInstance();
                break;
        }

        // change page action
        if (action.getType().equals("change page")) {
            String changePageResponse = page.changePage(action.getPage());
            if (changePageResponse.equals("err")) {
                if(getCurrentPage().equals("login") || changePageResponse.equals("register"))
                    setCurrentPage("logout");
                return "err";
            }
            else { // useeffect for specific pages if needed
                currentPage = action.getPage();
                switch (currentPage) {
                    case "logout":
                        setCurrentUser(null);
                        break;
                    case "movies":
                        //setLastSearched(null);
                        setCurrentMovies(availableMovies(moviesList, currentUser));
                        return "showMovies";
                    case "see details":
                        if(getCurrentMovies().size() < 1)
                            setCurrentMovies(availableMovies(moviesList, currentUser));
                        int checkMovie = checkDetails(action, currentMovies);
                        ArrayList<Movies> seeDetails = new ArrayList<Movies>();
                        if (checkMovie == -1) {
                            if (action.getMovie().length() < 1 && lastSearched != null) {
                                seeDetails.add(lastSearched);
                            } else {
                                setCurrentMovies(seeDetails);
                                return "err";
                            }
                        } else {
                            seeDetails.add(getCurrentMovies().get(checkMovie));
                        }
                        setCurrentMovies(seeDetails);
                        return "showMovies";
                    default:
                        break;
                }
                return "ok";
            }
        }

        // detect and execute action feature
        if (action.getType().equals("on page")) {
            if (currentPage.equals("movies"))
                setCurrentMovies(availableMovies(moviesList, currentUser));
            if(currentPage.equals("see details")) {
                if(currentMovies.size() < 1) {
                    return "err";
                }
                if (action.getMovie() == null) {
                    action.setMovie(currentMovies.get(0).getName());
                }
            }
            PageResponse actionResponse = page.action(action, users, currentMovies, currentUser);

            switch (actionResponse.getResponse()) {
                case "loginUser":
                    setCurrentUser(actionResponse.getUser());
                    setCurrentMovies(null);
                    setCurrentPage("auth");
                    break;
                case "registerUser":
                    setCurrentUser(actionResponse.getUser());
                    setCurrentPage("auth");
                    break;
                case "errLogin":
                    setCurrentPage("logout");
                    actionResponse.setResponse("err");
                    break;
                case "setMovies":
                    setCurrentMovies(actionResponse.getMovies());
                    if (currentMovies != null && currentMovies.size() > 0)
                        setLastSearched(currentMovies.get(0));
                    actionResponse.setResponse("showMovies");
                    break;
                case "updateUser":
                    setCurrentUser(actionResponse.getUser());
                    break;
                case "updateUserMovies": // purchase / watch / like / rate
                    setCurrentUser(actionResponse.getUser());
                    actionResponse.setResponse("showMovies");
                    break;
                default:
                    break;
            }
            return actionResponse.getResponse();
        }
        return "err";
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Users currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<Movies> getCurrentMovies() {
        return currentMovies;
    }

    public void setCurrentMovies(ArrayList<Movies> currentMovies) {
        this.currentMovies = currentMovies;
    }

    public Movies getLastSearched() {
        return lastSearched;
    }

    public void setLastSearched(Movies lastSearched) {
        this.lastSearched = lastSearched;
    }
    // methods used for modifying current data
    /**
     * find selection (called by search action) of movies
     * @param existingMovies = all movies
     * @param user = current user
     * @return search result
     */
    public ArrayList<Movies> availableMovies(ArrayList<Movies> existingMovies, Users user) {
        ArrayList<Movies> available = new ArrayList<Movies>();
        for (Movies movie : existingMovies) {
            if (!movie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
//                Movies aux = new Movies();
//                aux.setName(movie.getName());
//                aux.setActors(movie.getActors());
//                aux.setCountriesBanned(movie.getCountriesBanned());
//                aux.setDuration(movie.getDuration());
//                aux.setGenres(movie.getGenres());
//                aux.setNumLikes(movie.getNumLikes());
//                aux.setNumRatings(movie.getNumRatings());
//                aux.setRating(movie.getRating());
//                aux.setYear(movie.getYear());
//                available.add(aux);
                available.add(movie);
            }
        }
        return available;
    }
    public int checkDetails (Actions action, ArrayList<Movies> movies) {
        int detailIdx = -1;
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).getName().equals(action.getMovie())) {
                detailIdx = i;
                break;
            }
        }
        return detailIdx;
    }
}
