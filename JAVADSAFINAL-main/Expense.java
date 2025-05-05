public class Expense {
    int id, eventId;
    ExpenseCategory category;
    double amount;
    String date;

    public Expense(int id, ExpenseCategory category, double amount, String date, int eventId) {
        this.id = id;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.eventId = eventId;
    }

    public String toString() {
        return String.format("Expense #%d | Category: %s | Amount: Rs%.2f | Date: %s | EventID: %d",
                id, category, amount, date, eventId);
    }
}
