package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

public class UpdateTodo implements RequestHandler<Map<String,String>, String> {

    @Override
    public String handleRequest(Map<String, String> event, Context context) {
        System.out.println(UUID.fromString(event.get("id")));
        if(event.get("id") != null) {
            updateTodo(UUID.fromString(event.get("id")));
        } else
            throw new IllegalArgumentException("Invalid argument");
        return String.format("Todo is Updated");

    }

    public void updateTodo(UUID id) {
        String query = String.format("update public.todo set completed = true where id = '%s'", id);
        try (Connection con = new DataAccess().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
