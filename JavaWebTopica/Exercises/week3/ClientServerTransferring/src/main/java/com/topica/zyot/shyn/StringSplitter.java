package com.topica.zyot.shyn;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StringSplitter {
    private List<String> mParts;
    private Iterator<String> it;

    public StringSplitter(String msg) {
        String[] parts = msg.split(" ");
        mParts = Arrays.asList(parts);
        it = mParts.iterator();
    }

    public String next() {
        if (it.hasNext())
            return it.next();
        else return null;
    }

    public void skip(int step) {
        while (step-- > 0) {
            String word = this.next();
            if (word == null) {
                System.out.println("End of Splitter");
                break;
            }
        }
    }
}
