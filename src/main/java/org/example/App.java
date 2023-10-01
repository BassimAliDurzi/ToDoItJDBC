package org.example;

import model.Person;
import model.TodoItem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class App {
    public static void main(String[] args) {
//        Person ali = new Person("Ali", "Durzi");
//        ali.createPerson(ali);
//        Person bazi = new Person("Bazi", "Sawalmih");
//        bazi.createPerson(bazi);
//        Person wassim = new Person("Wassim", "Abbas");
//        wassim.createPerson(wassim);
//        Person maria = new Person("Maria", "AboZaid");
//        maria.createPerson(maria);

//        TodoItem readBook = new TodoItem("Read Book","Read 50 pages everyday",LocalDate.of(2023,10,15), 1);
//        readBook.create(readBook);

//        TodoItem running30km = new TodoItem("Runing 30 Km", "Run 3 km everyday", LocalDate.of(2023,10,10),2);
//        running30km.create(running30km);

//        TodoItem jobInterview = new TodoItem("jobInterview", "Job interview in Data Solutions company with Mr. Adam", LocalDate.of(2023,11,1), 4);
//        jobInterview.create(jobInterview);
//
//        Person sara = new Person("Sara", "Murad");
//        sara.createPerson(sara);
//        Person anika = new Person("Anika", "Ericsson");
//        anika.createPerson(anika);

//        TodoItem yogaClass = new TodoItem("Yoga","Training Yoga",LocalDate.of(2023,12,31), 5);
//        yogaClass.create(yogaClass);

        Person person = new Person();
        TodoItem todoItem = new TodoItem();

//        System.out.println("Find all users:");
//        person.findAll();
//        System.out.println("--------------------------------------------------");
//
//        System.out.println("Find user by id:");
//        int id = 4;
//        person.findById(id);
//        System.out.println("--------------------------------------------------");
//
//        System.out.println("Find user by name:");
//        person.findByName("Ali");
//        System.out.println("--------------------------------------------------");

//        System.out.println("Update the person's information");
//        person.setId(6);
//        person.setFirstName("Sara");
//        person.update(person);

//        System.out.println("Delete person");
//        person.deleteById(7);

        //todoItem.findAll();
        //todoItem.findById(3);

//        System.out.println("findByDoneStatus");
//        todoItem.findByDoneStatus(true);
//
//        System.out.println("findByAssigneeID");
//        todoItem.findByAssignee(1);

//        System.out.println("findByAssigneeName");
//        todoItem.findByAssignee("Bassim");
//
//        System.out.println("deleteById");
//        todoItem.deleteById(1);

        System.out.println("Update the item's information");
        todoItem.setTodoID(4);
//        todoItem.setTitle("Run 90 km");
//        todoItem.setDescription("Running 3 km everyday ");
//        todoItem.setDeadline(LocalDate.of(2023,11,11));
        todoItem.setDone(true);
        todoItem.updateItem(todoItem);


    }
}