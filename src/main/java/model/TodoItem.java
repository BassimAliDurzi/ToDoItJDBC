package model;

import interfaces.TodoItems;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class TodoItem implements TodoItems {
    private int todoID;
    private String title;
    private String description;
    private LocalDate deadline;
    private boolean done;
    private int assigneeId;
    String url = "jdbc:mysql://localhost:3306/todoit";
    String username = "root";
    String password = "root123";

    public TodoItem(int itemId, String title, String description, LocalDate deadline, boolean itemStatus, int personID, int personId, String pFirstName, String pLastName) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.assigneeId = personID;
    }

    public TodoItem(int todoID, String title, String description, LocalDate deadline, boolean done, int personID) {
        this.todoID = todoID;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
        this.assigneeId = personID;
    }

    public TodoItem() {

    }

    public int todoID() {
        return todoID;
    }

    public String title() {
        return title;
    }

    public String description() {
        return description;
    }

    public LocalDate deadline() {
        return deadline;
    }

    public boolean done() {
        return done;
    }

    public int personID() {
        return assigneeId;
    }

    public void setTodoID(int todoID) {
        this.todoID = todoID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "todoID=" + todoID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", done=" + done +
                ", personID=" + assigneeId +
                '}';
    }

    @Override
    public TodoItem create(TodoItem item) {
        String createItem = "INSERT INTO TODO_ITEM(todo_id, title, description, deadline, done, assignee_id) VALUES(?,?,?,?,?,?)";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(createItem, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, item.todoID);
            preparedStatement.setString(2, item.title);
            preparedStatement.setString(3, item.description);
            preparedStatement.setDate(4, Date.valueOf(item.deadline));
            preparedStatement.setBoolean(5, item.done);
            preparedStatement.setInt(6, item.assigneeId);

            int rowInserted = preparedStatement.executeUpdate();
            if (rowInserted > 0) {
                System.out.println("The item (" + item.title + ") was created successfully");
            } else {
                connection.rollback();
                throw new RuntimeException("Create process was not complete, A failed attempt");
            }
            try (
                    ResultSet generateKeys = preparedStatement.getGeneratedKeys()) {
                if (generateKeys.next()) {
                    int generatedItemId = generateKeys.getInt(1);
                    System.out.println("Generated item id: " + generatedItemId);
                }
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public Collection<TodoItem> findAll() {
        String findAll = "SELECT * FROM todo_item";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
        ) {
            ResultSet resultSet = statement.executeQuery(findAll);
            Collection<TodoItem> allTodoItem = new ArrayList<>();

            while (resultSet.next()) {
                int todoId = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                LocalDate deadline = resultSet.getDate(4).toLocalDate();
                boolean status = resultSet.getBoolean(5);
                int assigneeId = resultSet.getInt(6);

                TodoItem todoItem = new TodoItem(todoId, title, description, deadline, status, assigneeId);
                allTodoItem.add(todoItem);
            }
            allTodoItem.forEach(System.out::println);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TodoItem findById(int id) {
        String findItemById = "SELECT * FROM todo_item WHERE todo_id = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(findItemById);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int itemId = resultSet.getInt(1);
                String itemTitle = resultSet.getString(2);
                String itemDescription = resultSet.getString(3);
                LocalDate itemDeadline = resultSet.getDate(4).toLocalDate();
                boolean itemStatus = resultSet.getBoolean(5);
                int itemAssigneeId = resultSet.getInt(6);

                TodoItem todoItem = new TodoItem(itemId, itemTitle, itemDescription, itemDeadline, itemStatus, itemAssigneeId);
                System.out.println(todoItem);
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<TodoItem> findByDoneStatus(boolean isDone) {
        String findByStatus = "SELECT * FROM todo_item WHERE done = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(findByStatus);
        ) {
            preparedStatement.setBoolean(1, isDone);
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<TodoItem> todoItemByStatusList = new ArrayList<>();
            while (resultSet.next()) {
                int itemId = resultSet.getInt(1);
                String itemTitle = resultSet.getString(2);
                String itemDescription = resultSet.getString(3);
                LocalDate itemDeadline = resultSet.getDate(4).toLocalDate();
                boolean itemStatus = resultSet.getBoolean(5);
                int itemAssigneeId = resultSet.getInt(6);

                TodoItem todoItem = new TodoItem(itemId, itemTitle, itemDescription, itemDeadline, itemStatus, itemAssigneeId);
                todoItemByStatusList.add(todoItem);
            }
            todoItemByStatusList.forEach(System.out::println);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<TodoItem> findByAssignee(int id) {
        String findByAssigneeID = "SELECT * FROM todo_item WHERE assignee_id = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(findByAssigneeID);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Collection<TodoItem> todoItemList = new ArrayList<>();
            while (resultSet.next()) {
                int itemId = resultSet.getInt(1);
                String itemTitle = resultSet.getString(2);
                String itemDescription = resultSet.getString(3);
                LocalDate itemDeadline = resultSet.getDate(4).toLocalDate();
                boolean itemStatus = resultSet.getBoolean(5);
                int itemAssigneeId = resultSet.getInt(6);

                TodoItem todoItem = new TodoItem(itemId, itemTitle, itemDescription, itemDeadline, itemStatus, itemAssigneeId);
                todoItemList.add(todoItem);
            }
            todoItemList.forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<TodoItem> findByAssignee(Person name) {
        return null;
    }

    @Override
    public Collection<TodoItem> findByAssignee(String firstName) {
        String findByAssigneeName = "select * from todo_item join person on todo_item.assignee_id = person.person_id where person.first_name = ?";

        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(findByAssigneeName);
        ) {
            preparedStatement.setObject(1, firstName);
            ResultSet resultSet = preparedStatement.executeQuery();

            Collection<TodoItem> todoInfoList = new ArrayList<>();

            while (resultSet.next()) {
                int itemId = resultSet.getInt(1);
                String itemTitle = resultSet.getString(2);
                String itemDescription = resultSet.getString(3);
                LocalDate itemDeadline = resultSet.getDate(4).toLocalDate();
                boolean itemStatus = resultSet.getBoolean(5);
                int assigneeId = resultSet.getInt(6);

                TodoItem todoItemInfo = new TodoItem(itemId, itemTitle, itemDescription, itemDeadline, itemStatus, assigneeId);
                todoInfoList.add(todoItemInfo);
            }
            todoInfoList.forEach(System.out::println);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void updateItem(TodoItem item) {
        String updateTitle = "UPDATE todo_item SET title = ? WHERE todo_id = ?";
        String updateDescription = "UPDATE todo_item SET description = ? WHERE todo_id = ?";
        String updateDeadline = "UPDATE todo_item SET deadline = ? WHERE todo_id = ?";
        String updateStatus = "UPDATE todo_item SET done = ? WHERE todo_id = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatementUpdateStatus = connection.prepareStatement(updateTitle);
                PreparedStatement preparedStatementUpdateDescription = connection.prepareStatement(updateDescription);
                PreparedStatement preparedStatementUpdateDeadline = connection.prepareStatement(updateDeadline);
                PreparedStatement preparedStatementUpdateItemStatus = connection.prepareStatement(updateStatus);
        ) {
            int id = todoID();
            String title = title();
            String description = description();
            LocalDate deadline = deadline();
            boolean status = done();

            if (title != null) {
                preparedStatementUpdateStatus.setString(1, title);
                preparedStatementUpdateStatus.setInt(2, id);
                int titleUpdated = preparedStatementUpdateStatus.executeUpdate();
                if (titleUpdated > 0) {
                    System.out.println("The title was updated successfully");
                }
            }
            if (description != null) {
                preparedStatementUpdateDescription.setString(1, description);
                preparedStatementUpdateDescription.setInt(2, id);
                int descriptionUpdate = preparedStatementUpdateDescription.executeUpdate();
                if (descriptionUpdate > 0) {
                    System.out.println("The description was updated successfully");
                }
            }
            if (deadline != null) {
                preparedStatementUpdateDeadline.setDate(1, Date.valueOf(deadline));
                preparedStatementUpdateDeadline.setInt(2, id);
                int deadlineUpdate = preparedStatementUpdateDeadline.executeUpdate();
                if (deadlineUpdate > 0) {
                    System.out.println("The deadline was updated successfully");
                }
            }
            if (status || !status) {
                preparedStatementUpdateItemStatus.setBoolean(1, status);
                preparedStatementUpdateItemStatus.setInt(2, id);
                int statusUpdated = preparedStatementUpdateItemStatus.executeUpdate();
                if (statusUpdated > 0) {
                    System.out.println("The status was updated successfully");
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteById(int id) {
        String deleteTodoItem = "DELETE FROM todo_item WHERE todo_id = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(deleteTodoItem);
        ) {
            preparedStatement.setInt(1, id);
            int recordsDeleted = preparedStatement.executeUpdate();
            if (recordsDeleted > 0) {
                System.out.println("The item was deleted successfully!");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
