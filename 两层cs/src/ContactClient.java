import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ContactClient {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Socket clientSocket = null;

        try {
            while (true) {
                clientSocket = new Socket("localhost", PORT);
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                System.out.println("Choose an action:");
                System.out.println("1. Add Contact");
                System.out.println("2. Delete Contact");
                System.out.println("3. Update Contact");
                System.out.println("4. Search Contact");
                System.out.println("5. Get All Contacts");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                switch (choice) {
                    case 1:
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Address: ");
                        String address = scanner.nextLine();
                        System.out.print("Enter Phone: ");
                        String phone = scanner.nextLine();
                        addContact(out, in, name, address, phone);
                        break;
                    case 2:
                        System.out.print("Enter Name to Delete: ");
                        String deleteName = scanner.nextLine();
                        deleteContact(out, in, deleteName);
                        break;
                    case 3:
                        System.out.print("Enter Name to Update: ");
                        String updateName = scanner.nextLine();
                        System.out.print("Enter New Address: ");
                        String newAddress = scanner.nextLine();
                        System.out.print("Enter New Phone: ");
                        String newPhone = scanner.nextLine();
                        updateContact(out, in, updateName, newAddress, newPhone);
                        break;
                    case 4:
                        System.out.print("Enter Name to Search: ");
                        String searchName = scanner.nextLine();
                        searchContact(out, in, searchName);
                        break;
                    case 5:
                        getAllContacts(out, in);
                        break;
                    case 6:
                        // Close the streams and socket when done.
                        out.close();
                        in.close();
                        clientSocket.close();
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addContact(ObjectOutputStream out, ObjectInputStream in, String name, String address, String phone) {
        try {
            out.writeObject("add_contact");
            out.writeObject(new Contact(name, address, phone));

            String response = (String) in.readObject();
            System.out.println(response);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void deleteContact(ObjectOutputStream out, ObjectInputStream in, String name) {
        try {
            out.writeObject("delete_contact");
            out.writeObject(name);

            String response = (String) in.readObject();
            System.out.println(response);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void updateContact(ObjectOutputStream out, ObjectInputStream in, String name, String newAddress, String newPhone) {
        try {
            out.writeObject("update_contact");
            out.writeObject(name);
            out.writeObject(newAddress);
            out.writeObject(newPhone);

            String response = (String) in.readObject();
            System.out.println(response);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void getAllContacts(ObjectOutputStream out, ObjectInputStream in) {
        try {
            out.writeObject("get_all_contacts");

            @SuppressWarnings("unchecked")
            List<Contact> results = (List<Contact>) in.readObject();
            if (results.isEmpty()) {
                System.out.println("No contacts found.");
            } else {
                System.out.println("All Contacts:");
                for (Contact contact : results) {
                    System.out.println(contact);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void searchContact(ObjectOutputStream out, ObjectInputStream in, String name) {
        try {
            out.writeObject("search_contact");
            out.writeObject(name);

            @SuppressWarnings("unchecked")
            List<Contact> results = (List<Contact>) in.readObject();
            if (results.isEmpty()) {
                System.out.println("No contacts found for: " + name);
            } else {
                System.out.println("Search Results for " + name + ":");
                for (Contact contact : results) {
                    System.out.println(contact);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
