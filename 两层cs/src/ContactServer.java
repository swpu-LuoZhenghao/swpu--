import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContactServer {
    private static final int PORT = 12345;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/personal_contacts"; // 请根据你的数据库设置修改
    private static final String DB_USER = "root"; // 修改为你的数据库用户名
    private static final String DB_PASSWORD = "root"; // 修改为你的数据库密码

    public static void main(String[] args) {
        try {
            // 注册MySQL JDBC驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 创建服务器Socket并开始监听端口
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                System.out.println("Server is running on port " + PORT);
                while (true) {
                    try (Socket clientSocket = serverSocket.accept();
                         ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                         ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

                        String request = (String) in.readObject();
                        if (request.equals("add_contact")) {
                            Contact newContact = (Contact) in.readObject();
                            addContact(newContact);
                            out.writeObject("Contact added successfully.");
                        } else if (request.equals("delete_contact")) {
                            String name = (String) in.readObject();
                            deleteContact(name);
                            out.writeObject("Contact deleted successfully.");
                        } else if (request.equals("search_contact")) {
                            String searchName = (String) in.readObject();
                            List<Contact> results = searchContact(searchName);
                            out.writeObject(results);
                        } else if (request.equals("get_all_contacts")) {
                            List<Contact> allContacts = getAllContacts();
                            out.writeObject(allContacts);
                        } else if (request.equals("update_contact")) {
                            String name = (String) in.readObject();
                            String newAddress = (String) in.readObject();
                            String newPhone = (String) in.readObject();
                            String updateResult = updateContact(name, newAddress, newPhone);
                            out.writeObject(updateResult);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void addContact(Contact contact) {
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

    private static void deleteContact(String name) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM contacts WHERE name = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<Contact> searchContact(String searchName) {
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

    private static List<Contact> getAllContacts() {
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

    private static String updateContact(String name, String newAddress, String newPhone) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // 先检查联系人是否存在
            String checkQuery = "SELECT * FROM contacts WHERE name = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuery);
            checkStatement.setString(1, name);
            ResultSet resultSet = checkStatement.executeQuery();
            if (!resultSet.next()) {
                return "No contact found with name: " + name;
            }

            String query = "UPDATE contacts SET address = ?, phone = ? WHERE name = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, newAddress);
            preparedStatement.setString(2, newPhone);
            preparedStatement.setString(3, name);
            int updatedRows = preparedStatement.executeUpdate();

            if (updatedRows > 0) {
                return "Contact updated successfully.";
            } else {
                return "Failed to update contact.";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failed to update contact.";
        }
    }
}
