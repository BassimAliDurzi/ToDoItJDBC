package model;

import java.time.LocalDate;

public class TodoItem {
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
}
