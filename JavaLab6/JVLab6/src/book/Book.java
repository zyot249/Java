package book;

public class Book {

    private String name;

    private Author[] authors;

    private double price;

    private int qty = 0;

    public Book(String name, Author[] authors, double price) {
        this.name = name;
        this.authors = authors;
        this.price = price;
    }

    public Book(String name, Author[] authors, double price, int qty) {
        this.name = name;
        this.authors = authors;
        this.price = price;
        this.qty = qty;
    }

    public String getName() {
        return this.name;
    }

    public Author[] getAuthors() {
        return this.authors;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return this.qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String toString() {
        String string = "Book[name = " + this.name + ", authors{";
        for (Author author : authors)
            string += (author.toString() + ",");
        string = string.substring(0, string.length() - 1);
        string += "}, price = " + this.price + ", qty = " + this.qty + "]";
        return string;
    }

    public String getAuthorNames() {
        String authorNames = "";
        for (Author author : authors)
            authorNames += (author.getName() + ",");
        authorNames = authorNames.substring(0, authorNames.length() - 1);
        return authorNames;
    }

}
