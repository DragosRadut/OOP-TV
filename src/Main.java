import InputData.Actions;
import InputData.Input;
import InputData.Movies;
import InputData.Users;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        final String filePath1 = args[0];//"/home/waze/Desktop/poo/POOTV1/POO-TV_1/checker/resources/in/in.json";
        final String filePath2 = args[1];//"output.json";
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
        int cnt = 0;
        for(Actions act : actions) {
            // send input to interpreter
            String execResponse = interpret.exec(act, allUsers, existingMovies);
            //System.out.println(execResponse);
            if (execResponse.equals("err")) {
                outAux = out.generateError("Error");
                output.addPOJO(outAux);
            }
            else if (Arrays.asList(actionOutputCases).contains(execResponse)){
                outAux = out.generateOutput(null, interpret.getCurrentMovies(), interpret.getCurrentUser());
                output.addPOJO(outAux);
            }
            //System.out.println(interpret.getCurrentPage());
//            if(++cnt == 3)
//                break;
        }


        //output.addPOJO(inputData)
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        try {
            objectWriter.writeValue(new File(filePath2), output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
