package com.topica.zyot.shyn.main;

import com.topica.zyot.shyn.annotation.Query;

public class Test {
    public static void main(String[] args) {
        Book book = new Book("BOOK001","Doraemen", "Shyn", 100);
        try {
            String insert = Query.insert(book);
            if (insert != null)
                System.out.println(insert);

            String update = Query.update(book);
            if (update != null)
                System.out.println(update);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
