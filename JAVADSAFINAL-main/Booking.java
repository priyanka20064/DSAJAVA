public class Booking {
    Event event;
    Client client;

    public Booking(Event event, Client client) {
        this.event = event;
        this.client = client;
    }

    public String toString() {
        return String.format("Booking: %s for %s", event.name, client.name);
    }
}
