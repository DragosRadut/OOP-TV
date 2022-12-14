import InputData.Actions;
import InputData.Movies;
import InputData.Users;
import PageData.*;

import java.util.ArrayList;

public class Interpreter {
    private String currentPage;
    private Users currentUser;
    public String exec(final Actions action, ArrayList<Users> users, ArrayList<Movies> moviesList) {
        // determine current page type
        Page page = null;
        switch (currentPage) {
            case "logout": // same as logout
                page = new NonAuthPage();
                break;
            case "login":
                page = new LoginPage();
                break;
            case "register":
                page = new RegisterPage();
                break;
            case "auth":
                page = new AuthPage();
                break;
        }
        // change page action
        if (action.getType().equals("change page")) {
            if (page.changePage(action.getPage()).equals("err")) {
                return "err";
            }
            else {
                currentPage = action.getPage();
                return "ok";
            }
        }
        // detect and execute action feature
        if (action.getType().equals("on page")) {
            String actionResponse = page.action(action, users);
            switch (actionResponse) {
                case "loginUser":
                    setCurrentUser(users.get(findUserIndex(users, action)));
                    setCurrentPage("auth");
                    break;
                case "registerUser":
                    setCurrentUser(users.get(findUserIndex(users, action)));
                    setCurrentPage("auth");
                    break;
                default:
                    break;
            }
            return actionResponse;
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
    public int findUserIndex(ArrayList<Users> users, Actions act) {
        int idx = 0;
        for (Users userIter : users) {
            if (userIter.getCredentials().getName().equals(act.getCredentials().getName()))
                return idx;
            idx++;
        }
        return -1;
    }
}
