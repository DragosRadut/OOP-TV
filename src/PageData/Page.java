package PageData;

import InputData.Actions;
import InputData.Users;

import java.util.ArrayList;

public interface Page {
    public String changePage(final String whereTo);
    public String action(final Actions action, ArrayList<Users> users);
}
