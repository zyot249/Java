package com.topica.zyot.shyn;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ChangeFinal {
    private static void changeFinalValue(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        setModifierNotFinal(field);
        field.set(null, newValue);
    }

    private static void setModifierNotFinal(Field field) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(Rectangle.NAME);
        changeFinalValue(Rectangle.class.getField("NAME"), "TRIANGLE");
        System.out.println(Rectangle.NAME);

        System.out.println(Rectangle.NUMBER_OF_POINTS);
        changeFinalValue(Rectangle.class.getField("NUMBER_OF_POINTS"), 5);
        System.out.println(Rectangle.NUMBER_OF_POINTS);

//        changeFinalValue(Rectangle.class.getField("FOUR"), 6);
//        System.out.println(Rectangle.FOUR);
    }

}
