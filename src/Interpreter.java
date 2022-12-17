import input.data.Actions;
import input.data.Movies;
import input.data.Users;
import page.data.UpgradesPage;
import page.data.MoviesPage;
import page.data.LoginPage;
import page.data.PageResponse;
import page.data.RegisterPage;
import page.data.Page;
import page.data.AuthPage;
import page.data.NonAuthPage;
import page.data.SeeDetailsPage;

import java.util.ArrayList;

final class Interpreter {
    private String currentPage;
    private Users currentUser;
    private ArrayList<Movies> currentMovies;
    private ArrayList<Movies> lastSearched = new ArrayList<Movies>();
    public String exec(final Actions action, final ArrayList<Users> users,
                       final ArrayList<Movies> moviesList) {
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
            default:
                break;
        }

        // change page action
        if (action.getType().equals("change page")) {
            String changePageResponse = page.changePage(action.getPage());
            if (changePageResponse.equals("err")) {
                // login / register errors should trigger redirection to nonauth
                if (getCurrentPage().equals("login") || changePageResponse.equals("register")) {
                    setCurrentPage("logout");
                }
                return "err";
            } else { // useeffect for specific pages if needed
                currentPage = action.getPage();
                switch (currentPage) {
                    case "logout":
                        setCurrentUser(null);
                        break;
                    case "movies":
                        setCurrentMovies(availableMovies(moviesList, currentUser));
                        return "showMovies";
                    case "see details":
                        if (getCurrentMovies().size() < 1) {
                            setCurrentMovies(availableMovies(moviesList, currentUser));
                        }
                        // check if movie given as input exists
                        int checkMovie = checkDetails(action, currentMovies);
                        ArrayList<Movies> seeDetails = new ArrayList<Movies>();
                        if (checkMovie == -1) {
                            if (action.getMovie().length() < 1 && lastSearched != null) {
                                seeDetails = lastSearched;
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
            if (currentPage.equals("movies")) {
                setCurrentMovies(availableMovies(moviesList, currentUser));
            }
            if (currentPage.equals("see details")) {
                if (currentMovies.size() < 1) {
                    return "err";
                }
                if (action.getMovie() == null) {
                    action.setMovie(currentMovies.get(0).getName());
                }
            }
            // execute action depending on response form page
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
                    if (currentMovies != null && currentMovies.size() > 0) {
                        for (Movies movies : actionResponse.getMovies()) {
                            getLastSearched().add(movies);
                        }
                    }
                    actionResponse.setResponse("showMovies"); // generate output
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

    public void setCurrentPage(final String currentPage) {
        this.currentPage = currentPage;
    }

    public Users getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(final Users currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<Movies> getCurrentMovies() {
        return currentMovies;
    }

    public void setCurrentMovies(final ArrayList<Movies> currentMovies) {
        this.currentMovies = currentMovies;
    }

    public ArrayList<Movies> getLastSearched() {
        return lastSearched;
    }

    public void setLastSearched(final ArrayList<Movies> lastSearched) {
        this.lastSearched = lastSearched;
    }

    /**
     * create selection af available movies in user's country
     * @param existingMovies = all movies
     * @param user = current user
     * @return search result
     */
    public ArrayList<Movies> availableMovies(final ArrayList<Movies> existingMovies,
                                             final Users user) {
        ArrayList<Movies> available = new ArrayList<Movies>();
        for (Movies movie : existingMovies) {
            if (!movie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
                available.add(movie);
            }
        }
        return available;
    }

    /**
     * check if movie given in input exists in movie list
     * @param action = action input
     * @param movies = available movies
     * @return
     */
    public int checkDetails(final Actions action, final ArrayList<Movies> movies) {
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
