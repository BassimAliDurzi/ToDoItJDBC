package model;

import interfaces.People;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class Person implements People {
    private int id;
    private String firstName;
    private String lastName;
    private final String url = "jdbc:mysql://localhost:3306/todoit";
    private final String username = "root";
    private final String password = "root123";

    public Person(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person() {
    }

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String firstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String lastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public Person createPerson(Person person) {
        String createPerson = "INSERT INTO PERSON(person_id, first_name, last_name) values(?,?,?)";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(createPerson, PreparedStatement.RETURN_GENERATED_KEYS);
        ) {
            connection.setAutoCommit(false);
            preparedStatement.setInt(1, person.id);
            preparedStatement.setString(2, person.firstName);
            preparedStatement.setString(3, person.lastName);

            int rowInserted = preparedStatement.executeUpdate();
            if (rowInserted > 0) {
                System.out.println(firstName + " " + lastName + " created successfully!");
            } else {
                connection.rollback();
                throw new RuntimeException("Create process was not complete, A failed attempt");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedPersonId = generatedKeys.getInt(1);
                    System.out.println("Generated person ID: " + generatedPersonId);
                }
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public Collection<Person> findAll() {
        Connection connection = null;
        String findAllPersons = "SELECT * FROM PERSON";
        try {
            connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllPersons);

            Collection<Person> personsList = new ArrayList<>();

            while (resultSet.next()) {
                int personId = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);

                Person person = new Person(personId, firstName, lastName);
                personsList.add(person);
            }
            personsList.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null)
                    connection.close();
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public Person findById(int id) {
        Connection connection = null;
        String findById = "SELECT * FROM PERSON WHERE person_id = ?";
        try {
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(findById);

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int personId = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);

                Person person = new Person(personId, firstName, lastName);
                System.out.println(person.toString());
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null){
                    connection.close();
                }
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public Collection<Person> findByName(String name) {
        Connection connection = null;
        String findPersonByName = "SELECT * FROM PERSON WHERE first_name = ?";
        try {
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(findPersonByName);

            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            Collection<Person> personsList = new ArrayList<>();
            while (resultSet.next()) {
                int personId = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                Person person = new Person(personId, firstName, lastName);
                personsList.add(person);
            }
            personsList.forEach(System.out::println);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null)
                    connection.close();
            }catch (SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public Person update(Person person) {
        String updateFirstNameQuery = "UPDATE PERSON SET first_name = ? WHERE person_id = ?";
        String updateLastNameQuery = "UPDATE PERSON SET last_name = ? WHERE person_id = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatementUpdateFirstName = connection.prepareStatement(updateFirstNameQuery);
                PreparedStatement preparedStatementUpdateLastName = connection.prepareStatement(updateLastNameQuery);
        ) {
            int id = person.id();
            String personFirstName = person.firstName();
            String personLastName = person.lastName();

            if (personFirstName != null) {
                preparedStatementUpdateFirstName.setString(1, personFirstName);
                preparedStatementUpdateFirstName.setInt(2, id);
                int firstNameUpdated = preparedStatementUpdateFirstName.executeUpdate();
                if (firstNameUpdated > 0) {
                    System.out.println("First name was updated Successfully");
                }
            }
            if (personLastName != null) {
                preparedStatementUpdateLastName.setString(1, personLastName);
                preparedStatementUpdateLastName.setInt(2, id);
                int lastNameUpdated = preparedStatementUpdateLastName.executeUpdate();
                if (lastNameUpdated > 0) {
                    System.out.println("Last name was updated Successfully");
                }
            }
            connection.close();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        String deletePerson = "DELETE FROM person WHERE person_id = ?";
        try (
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement preparedStatement = connection.prepareStatement(deletePerson);
        ) {
            preparedStatement.setInt(1, id);
            int recordsDeleted = preparedStatement.executeUpdate();
            if (recordsDeleted > 0) {
                System.out.println("The person was deleted successfully!");
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
