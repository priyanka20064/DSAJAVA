public class Client {
    int id;
    String name, contact;

    public Client(int id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }

    public String toString() {
        return String.format("Client ID: %d | Name: %s | Contact: %s", id, name, contact);
    }
}
