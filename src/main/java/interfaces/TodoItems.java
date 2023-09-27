package interfaces;

import model.Person;
import model.TodoItem;

import java.util.ArrayList;
import java.util.Collection;

public interface TodoItems {
    TodoItem create(TodoItem item);
    Collection<TodoItem> findAll();
    TodoItem findById(int id);
    Collection<TodoItem> findByDoneStatus(boolean isDone);
    Collection<TodoItem> findByAssignee(int id);
    Collection<TodoItem> findByAssignee(Person name);
    void updateItem(TodoItem item);
    boolean deleteById(int id);

}
