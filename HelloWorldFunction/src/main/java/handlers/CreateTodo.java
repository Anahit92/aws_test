package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.sql.*;
import java.util.Map;
import java.util.UUID;

public class CreateTodo implements RequestHandler<Map<String,String>, String> {

    @Override
    public String handleRequest(Map<String,String> event, Context context) {
        if(event.get("title") != null) {
            addTodo(event.get("title"));
        } else
            throw new IllegalArgumentException("Invalid argument");
        return "Todo is added";
    }

    public void addTodo(String title) {
        String query = "insert into public.todo values (?, ?, ?)";
        try (Connection con = new DataAccess().getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setObject(1, UUID.randomUUID());
            preparedStatement.setString(2, title);
            preparedStatement.setBoolean(3, false);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
