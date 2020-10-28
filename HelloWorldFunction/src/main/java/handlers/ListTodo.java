package handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListTodo implements RequestHandler<Map<String,String>, List<Todo>> {

    @Override
    public List<Todo> handleRequest(Map<String,String> event, Context context) {
        if(event.get("exclude_completed").equals("1"))
            return getTodoPendingList();
        else if(event.get("exclude_completed").equals("0"))
            return getTodoAllList();
        else
            throw new IllegalArgumentException("Invalid argument");
    }

    public List<Todo> getTodoPendingList() {
        List<Todo> list = new ArrayList();

        try (Connection con = new DataAccess().getConnection();
             Statement statement = con.createStatement()) {
            try(ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM public.todo where completed = false")) {
                while (resultSet.next()) {
                    Todo td = new Todo(null, resultSet.getString("title"), resultSet.getBoolean("completed"));
                    list.add(td);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Todo> getTodoAllList() {
        List<Todo> list = new ArrayList();
        try (Connection con = new DataAccess().getConnection();
             Statement statement = con.createStatement()) {
            try(ResultSet resultSet = statement.executeQuery("SELECT * FROM public.todo")){
                while (resultSet.next()) {
                    Todo td = new Todo(null,resultSet.getString("title"), resultSet.getBoolean("completed"));
                    list.add(td);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
