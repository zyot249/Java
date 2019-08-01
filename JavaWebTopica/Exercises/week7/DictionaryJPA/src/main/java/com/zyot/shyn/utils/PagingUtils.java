package com.zyot.shyn.utils;

public class PagingUtils {
    private PagingUtils() {

    }

    public static int getStartIndex(int curIndex) {
        if (curIndex == 1) {
            return 1;
        } else {
            return curIndex - 1;
        }
    }

    public static int getEndIndex(int curIndex, int numOfPages) {
        if (curIndex == 1) {
            if (numOfPages == 1) {
                return 1;
            } else {
                return 2;
            }
        } else {
            if (curIndex == numOfPages)
                return curIndex;
            else return curIndex + 1;
        }
    }
}
