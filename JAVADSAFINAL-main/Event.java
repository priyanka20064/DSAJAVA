public class Event {
    int id;
    String name, date, venue;

    public Event(int id, String name, String date, String venue) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.venue = venue;
    }

    public String toString() {
        return String.format("Event ID: %d | Name: %s | Date: %s | Venue: %s", id, name, date, venue);
    }
}
