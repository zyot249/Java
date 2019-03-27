package com.objectcomparing;

import com.library.Book;

public class Memory {
    public static void main(String[] args) {
        int x1 = 3, x2 = 7;
        swap1(x1, x2);
        System.out.println("x1: " + x1);
        System.out.println("x2: " + x2);

        Book mathBook = new Book(1, "Math Book", 13, 13);
        Book linuxBook = new Book(2, "Linux Book", 10, 10);
        swap2(mathBook, linuxBook);
        mathBook.showBookInfo();
        linuxBook.showBookInfo();


        Integer n1 = new Integer(47);
        Integer n2 = new Integer(47);
        System.out.println("Result 1: " + (x1 == x2));
        System.out.println("Result 2: " + (n1 == n2));
        System.out.println("Result 3: " + (n1 != n2));
        System.out.println("Result 4: " + n1.equals(n2));
        System.out.println("Result 5: " + mathBook.equals(linuxBook));

    }

    public static void swap1(int a1, int a2) {
        int tmp = a1;
        a1 = a2;
        a2 = tmp;
    }

    public static void swap2(Book b1, Book b2) {
        Book tmp = b1;
        b1 = b2;
        b2 = tmp;
    }
}
