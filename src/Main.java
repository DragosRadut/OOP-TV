import input.data.Actions;
import input.data.Input;
import input.data.Movies;
import input.data.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    /**
     * main method
     * @param args = input file, output file
     */
    public static void main(final String[] args) {
        final String filePath1 = args[0];
        final String filePath2 = args[1];
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData;
        try {
            inputData = objectMapper.readValue(new File(filePath1), Input.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ArrayNode output = objectMapper.createArrayNode();

        // store data from input
        ArrayList<Users> allUsers = new ArrayList<Users>();
        ArrayList<Movies> existingMovies = new ArrayList<Movies>();
        ArrayList<Actions> actions = new ArrayList<Actions>();
        for (Users usersInput : inputData.getUsers()) {
            allUsers.add(usersInput);
        }
        for (Movies moviesInput : inputData.getMovies()) {
            existingMovies.add(moviesInput);
        }
        for (Actions actionInput : inputData.getActions()) {
            actions.add(actionInput);
        }

        Output out = new Output();
        ObjectNode outAux = null;
        Interpreter interpret = new Interpreter();
        interpret.setCurrentPage("logout");
        interpret.setCurrentUser(null);
        interpret.setCurrentMovies(null);
        String[] actionOutputCases = new String[] {"registerUser", "loginUser", "showMovies"};

        // iterate through actions and execute
        for (Actions act : actions) {
            // send input to interpreter
            String execResponse = interpret.exec(act, allUsers, existingMovies);
            if (execResponse.equals("err")) {
                outAux = out.generateError("Error");
                output.addPOJO(outAux);
            } else if (Arrays.asList(actionOutputCases).contains(execResponse)) {
                outAux = out.generateOutput(null, interpret.getCurrentMovies(),
                        interpret.getCurrentUser());
                output.addPOJO(outAux);
            }
        }

        // output
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        try {
            objectWriter.writeValue(new File(filePath2), output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
