import java.text.SimpleDateFormat;
import java.util.*;

public class BookingManager {
    private Scanner sc = new Scanner(System.in);

    private LinkedList<Expense> expenses = new LinkedList<Expense>();
    private Queue<Booking> bookings = new LinkedList<Booking>();
    private BST<Client> clientBST = new BST<Client>(new Comparator<Client>() {
        public int compare(Client c1, Client c2) {
            return c1.id - c2.id;
        }
    });
    private BST<Event> eventBST = new BST<Event>(new Comparator<Event>() {
        public int compare(Event e1, Event e2) {
            return e1.id - e2.id;
        }
    });

    private double totalBudget = 0;

    private boolean isFutureDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            Date inputDate = sdf.parse(dateStr);
            Date today = sdf.parse(sdf.format(new Date()));
            return inputDate.equals(today) || inputDate.after(today);
        } catch (Exception e) {
            return false;
        }
    }

    // Event Functions
    public void addEvent() {
        System.out.print("Enter Event ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Event Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Event Date (dd-MM-yyyy): ");
        String date = sc.nextLine();
        System.out.print("Enter Venue: ");
        String venue = sc.nextLine();

        if (!isFutureDate(date)) {
            System.out.println("Error: Event date must be today or in the future.");
            return;
        }

        Event newEvent = new Event(id, name, date, venue);
        eventBST.insert(newEvent);
        System.out.println("Event added.");
    }

    public void viewEvents() {
        System.out.println("=== Events in Sorted Order ===");
        List<Event> events = eventBST.inOrder();
        for (Event e : events) {
            System.out.println(e);
        }
    }

    public void searchEvent() {
        System.out.print("Enter event name to search: ");
        String searchName = sc.nextLine().toLowerCase();
        List<Event> matchedEvents = new ArrayList<Event>();
        List<Event> events = eventBST.inOrder();
        for (Event event : events) {
            if (event.name.toLowerCase().contains(searchName)) {
                matchedEvents.add(event);
            }
        }
        if (matchedEvents.isEmpty()) {
            System.out.println("No events found with the given name.");
        } else {
            System.out.println("=== Search Results ===");
            for (Event e : matchedEvents) {
                System.out.println(e);
            }
        }
    }

    public void updateEvent() {
        System.out.print("Enter Event ID to update: ");
        int id = sc.nextInt(); sc.nextLine();
        Event found = eventBST.search(new Event(id, "", "", ""));
        if (found == null) {
            System.out.println("Event not found.");
            return;
        }
        System.out.print("New Name: ");
        found.name = sc.nextLine();
        System.out.print("New Date (dd-MM-yyyy): ");
        String newDate = sc.nextLine();
        if (!isFutureDate(newDate)) {
            System.out.println("Error: Date must be today or in the future.");
            return;
        }
        found.date = newDate;
        System.out.print("New Venue: ");
        found.venue = sc.nextLine();
        System.out.println("Event updated.");
    }

    public void deleteEvent() {
        System.out.println("Deletion not implemented in BST for safety. Mark manually if needed.");
    }

    // Client Functions
    public void addClient() {
        System.out.print("Enter Client ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Client Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Client Contact (10 digits): ");
        String contact = sc.nextLine();

        if (!contact.matches("\\d{10}")) {
            System.out.println("Invalid contact number.");
            return;
        }

        clientBST.insert(new Client(id, name, contact));
        System.out.println("Client added.");
    }

    public void viewClients() {
        System.out.println("=== Clients in Sorted Order ===");
        List<Client> clients = clientBST.inOrder();
        for (Client c : clients) {
            System.out.println(c);
        }
    }

    // Booking
    public void bookEvent() {
        System.out.print("Enter Event ID: ");
        int eventId = sc.nextInt();
        System.out.print("Enter Client ID: ");
        int clientId = sc.nextInt(); sc.nextLine();
        Event event = eventBST.search(new Event(eventId, "", "", ""));
        Client client = clientBST.search(new Client(clientId, "", ""));
        if (event == null || client == null) {
            System.out.println("Invalid Event or Client.");
            return;
        }
        bookings.add(new Booking(event, client));
        System.out.println("Booking successful.");
    }

    public void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings.");
            return;
        }
        for (Booking b : bookings) {
            System.out.println(b);
        }
    }

    // Budget & Expenses
    public void setBudget() {
        System.out.print("Enter total budget: ₹");
        totalBudget = sc.nextDouble(); sc.nextLine();
        System.out.println("Budget set to ₹" + totalBudget);
    }

    public void addExpense() {
        System.out.print("Enter Event ID: ");
        int eventId = sc.nextInt(); sc.nextLine();
        Event event = eventBST.search(new Event(eventId, "", "", ""));
        if (event == null) {
            System.out.println("Invalid event.");
            return;
        }

        System.out.println("\nSelect Expense Category:");
        ExpenseCategory[] categories = ExpenseCategory.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.printf("%d. %s\n", i + 1, categories[i]);
        }
        System.out.print("Enter category number: ");
        int categoryChoice = sc.nextInt(); sc.nextLine();
        if (categoryChoice < 1 || categoryChoice > categories.length) {
            System.out.println("Invalid category.");
            return;
        }

        ExpenseCategory category = categories[categoryChoice - 1];
        System.out.print("Enter amount: ₹");
        double amount = sc.nextDouble(); sc.nextLine();
        System.out.print("Enter date (dd-MM-yyyy): ");
        String date = sc.nextLine();
        if (!isFutureDate(date)) {
            System.out.println("Invalid date.");
            return;
        }

        expenses.add(new Expense(expenses.size() + 1, category, amount, date, eventId));
        System.out.println("Expense added.");
    }

    public void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses.");
            return;
        }

        double totalSpent = 0;
        for (Expense e : expenses) {
            totalSpent += e.amount;
        }

        System.out.println("\n=== Expense Report ===");
        System.out.printf("Total Budget: Rs%.2f\n", totalBudget);
        System.out.printf("Total Spent: Rs%.2f\n", totalSpent);
        System.out.printf("Remaining: Rs%.2f\n", totalBudget - totalSpent);

        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    public void removeExpense() {
        System.out.print("Enter Expense ID to remove: ");
        int id = sc.nextInt(); sc.nextLine();
        boolean removed = false;
        Iterator<Expense> it = expenses.iterator();
        while (it.hasNext()) {
            if (it.next().id == id) {
                it.remove();
                removed = true;
                break;
            }
        }
        System.out.println(removed ? "Expense removed." : "Expense not found.");
    }

    public void showBalance() {
        double spent = 0;
        for (Expense e : expenses) {
            spent += e.amount;
        }
        System.out.printf("\nBudget: Rs%.2f | Spent: Rs%.2f | Remaining: Rs%.2f\n", totalBudget, spent, totalBudget - spent);
    }
}
