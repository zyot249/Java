public class Book {
    private int bookID;
    private String bookTitle;
    private int amount;
    private int available;


    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public Book() {

    }

    public Book(int bookID, String bookTitle, int amount, int available) {
//        setBookID(bookID);
//        setBookTitle(bookTitle);
//        setAmount(amount);
//        setAvailable(available);

        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.amount = amount;
        this.available = available;
    }

    public void showBookInfo() {
        System.out.println("=== Book Info ====");
        System.out.println("Book Title: " + getBookTitle());
        System.out.println("Book ID: " + getBookID());
        System.out.println("Total amount: " + getAmount());
        System.out.println("Available: " + getAvailable());
    }

    public void updateAvailable(int available) {
        setAvailable(available);
    }
}
