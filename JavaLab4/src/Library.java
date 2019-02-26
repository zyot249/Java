import java.util.Scanner;

public class Library {
    private Book[] books;
    private String libraryTitle;
    private static int nbBooks;

    public static final int MAX_NUMBER_BOOKS = 100;


    public Library(String title) {
        this.libraryTitle = title;
        Library.nbBooks = 0;
        this.books = new Book[MAX_NUMBER_BOOKS];
    }

    public void addNewBook() {
        if (Library.nbBooks >= MAX_NUMBER_BOOKS)
            System.out.println("The Library is overloading");
        else {
            Scanner sc = new Scanner(System.in);
            System.out.println("-------------------------------------------");
            System.out.println("Enter book's information:");
            System.out.print("(1) Book ID: ");
            int id = sc.nextInt();
            System.out.print("(2) Book Title: ");
            sc.nextLine();
            String title = sc.nextLine();
            System.out.print("(3) Amount: ");
            int amount = sc.nextInt();

            Book newBook = new Book(id, title, amount, amount);
            books[Library.nbBooks++] = newBook;

        }
    }

    public boolean findBook(int bookID) {
        for (int i = 0; i < Library.nbBooks; i++) {
            Book curBook = this.books[i];
            if (curBook.getBookID() == bookID) {
                if (curBook.getAvailable() > 0) {
                    curBook.showBookInfo();
                    return true;
                }
            }
        }
        return false;
    }

    public void borrowBook(int bookID) {
        for (int i = 0; i < Library.nbBooks; i++) {
            Book curBook = this.books[i];
            if (curBook.getBookID() == bookID) {
                if (curBook.getAvailable() > 0) {
                    curBook.showBookInfo();
                    System.out.println("\n\nBorrow Successfully");
                    curBook.updateAvailable(curBook.getAvailable() - 1);
                    curBook.showBookInfo();
                } else {
                    System.out.println("This book is not available!");
                }
                return;
            }
        }
        System.out.println("The Library doesn't have this book!");
    }

    public void returnBook(int bookID) {
        for (int i = 0; i < Library.nbBooks; i++) {
            Book curBook = this.books[i];
            if (curBook.getBookID() == bookID) {
                System.out.println("The book is returned successfully");
                curBook.updateAvailable(curBook.getAvailable() + 1);
                curBook.showBookInfo();
                return;
            }
        }
        System.out.println("This book is not belong to the Library!");
    }

    public void showLibraryInfo() {
        if (Library.nbBooks == 0) {
            System.out.println("The Library doesn't have any book!");
            return;
        }
        System.out.println("\n== Library Info ==");
        System.out.println("Name : " + this.libraryTitle);
        for (int i = 0; i < Library.nbBooks; i++) {
            this.books[i].showBookInfo();
            System.out.println();
        }
    }
}
