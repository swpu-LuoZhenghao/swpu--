import java.io.*;
import java.net.*;
import java.util.List;

public class ContactServer {
    private static final int PORT = 12345;
    private ContactService contactService;

    public ContactServer() {
        contactService = new ContactService();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) {

                    String request = (String) in.readObject();
                    if (request.equals("add_contact")) {
                        Contact newContact = (Contact) in.readObject();
                        contactService.addContact(newContact);
                        out.writeObject("Contact added successfully.");
                    } else if (request.equals("delete_contact")) {
                        String name = (String) in.readObject();
                        contactService.deleteContact(name);
                        out.writeObject("Contact deleted successfully.");
                    } else if (request.equals("update_contact")) {
                        String name = (String) in.readObject();
                        String newAddress = (String) in.readObject();
                        String newPhone = (String) in.readObject();
                        contactService.updateContact(name, newAddress, newPhone);
                        out.writeObject("Contact updated successfully.");
                    } else if (request.equals("search_contact")) {
                        String searchName = (String) in.readObject();
                        List<Contact> results = contactService.searchContact(searchName);
                        out.writeObject(results);
                    } else if (request.equals("get_all_contacts")) {
                        List<Contact> allContacts = contactService.getAllContacts();
                        out.writeObject(allContacts);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ContactServer server = new ContactServer();
        server.start();
    }
}
