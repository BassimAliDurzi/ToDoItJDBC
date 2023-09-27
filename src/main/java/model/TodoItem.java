package model;

import interfaces.TodoItems;

import java.time.LocalDate;
import java.util.Collection;

public class TodoItem implements TodoItems {
    private int todoID;
    private String title;
    private String description;
    private LocalDate deadline;
    private boolean done;
    private Person personID;

    public TodoItem(String title, String description, LocalDate deadline, boolean done, Person personID) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
        this.personID = personID;
    }

    public TodoItem(int todoID, String title, String description, LocalDate deadline, boolean done, Person personID) {
        this.todoID = todoID;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
        this.personID = personID;
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

    public Person personID() {
        return personID;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "todoID=" + todoID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", done=" + done +
                ", personID=" + personID +
                '}';
    }

    @Override
    public TodoItem create(TodoItem item) {
        return null;
    }

    @Override
    public Collection<TodoItem> findAll() {
        return null;
    }

    @Override
    public TodoItem findById(int id) {
        return null;
    }

    @Override
    public Collection<TodoItem> findByDoneStatus(boolean isDone) {
        return null;
    }

    @Override
    public Collection<TodoItem> findByAssignee(int id) {
        return null;
    }

    @Override
    public Collection<TodoItem> findByAssignee(Person name) {
        return null;
    }

    @Override
    public void updateItem(TodoItem item) {

    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
