import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactService {
    private final String DB_URL = "jdbc:mysql://localhost:3306/personal_contacts";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "root";

    public void addContact(Contact contact) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO contacts (name, address, phone) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, contact.getName());
            preparedStatement.setString(2, contact.getAddress());
            preparedStatement.setString(3, contact.getPhone());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteContact(String name) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM contacts WHERE name = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateContact(String name, String newAddress, String newPhone) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "UPDATE contacts SET address = ?, phone = ? WHERE name = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, newAddress);
            preparedStatement.setString(2, newPhone);
            preparedStatement.setString(3, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Contact> searchContact(String searchName) {
        List<Contact> results = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM contacts WHERE name LIKE ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, "%" + searchName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                Contact contact = new Contact(name, address, phone);
                results.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<Contact> getAllContacts() {
        List<Contact> results = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM contacts";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                Contact contact = new Contact(name, address, phone);
                results.add(contact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
