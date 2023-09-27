package interfaces;

import model.Person;

import java.util.ArrayList;
import java.util.Collection;

public interface People {
    Person createPerson(Person person);
    Collection<Person> findAll();
    Person findById(int id);
    Collection<Person> findByName(String name);
    Person update(Person person);
    boolean deleteById(int id);
}
