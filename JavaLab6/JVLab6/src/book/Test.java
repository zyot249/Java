package book;

public class Test {


    public static void main(String[] args) {
        Author[] authors = new Author[3];
        authors[0] = new Author("shyn", "zyot@gmail.com",'m');
        authors[1] = new Author("shyn1", "zyot1@gmail.com",'m');
        authors[2] = new Author("shyn2", "zyot2@gmail.com",'f');

        Book xlBook = new Book("xlBook",authors, 3.456,12);

        System.out.println(xlBook.toString());
    }
}
