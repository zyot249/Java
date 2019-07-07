package com.topica.zyot.shyn.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class Query {
    @Insert
    public static String insert(Object obj) throws IllegalAccessException {
        Class clazz = obj.getClass();
        Annotation[] classAnnotations = clazz.getDeclaredAnnotations();
        for (Annotation classAnnotation : classAnnotations) {
            if (classAnnotation instanceof Entity) {
                StringBuilder fieldStr = new StringBuilder();
                StringBuilder valueStr = new StringBuilder();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
                    for (Annotation fieldAnnotation : fieldAnnotations) {
                        if (fieldAnnotation instanceof Column) {
                            field.setAccessible(true);
                            Column column = (Column) fieldAnnotation;
                            boolean isID = column.id();
                            if (isID) {
                                if (field.get(obj) == null)
                                    return null;
                            }
                            fieldStr.append("\"")
                                    .append(field.getName())
                                    .append("\"")
                                    .append(",");
                            if (field.getType().getSimpleName().equalsIgnoreCase("String"))
                                valueStr.append("\'")
                                        .append(field.get(obj).toString())
                                        .append("\'")
                                        .append(",");
                            else valueStr.append(field.get(obj).toString())
                                    .append(",");
                            break;
                        }
                    }
                }
                fieldStr.deleteCharAt(fieldStr.length() - 1);
                valueStr.deleteCharAt(valueStr.length() - 1);
                return "INSERT INTO \"" +
                        clazz.getSimpleName() +
                        "\" (" +
                        fieldStr.toString() +
                        ") VALUES (" +
                        valueStr.toString() +
                        ");";
            }
        }
        return null;
    }

    @Update
    public static String update(Object obj) throws IllegalAccessException {
        Class clazz = obj.getClass();
        Annotation[] classAnnotations = clazz.getDeclaredAnnotations();
        for (Annotation classAnnotation : classAnnotations) {
            if (classAnnotation instanceof Entity) {
                StringBuilder updatedValue = new StringBuilder();
                StringBuilder condition = new StringBuilder();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    Annotation[] fieldAnnotations = field.getDeclaredAnnotations();
                    for (Annotation fieldAnnotation : fieldAnnotations) {
                        if (fieldAnnotation instanceof Column) {
                            field.setAccessible(true);
                            Column column = (Column) fieldAnnotation;
                            boolean isID = column.id();
                            if (isID) {
                                if (field.get(obj) == null)
                                    return null;
                                else {
                                    if (condition.length() != 0)
                                        condition.append(" AND ");
                                    condition.append("\"")
                                            .append(field.getName())
                                            .append("\" = \'")
                                            .append(field.get(obj).toString())
                                            .append("\'");
                                }
                            } else {
                                if (updatedValue.length() != 0)
                                    updatedValue.append(", ");
                                updatedValue.append("\"")
                                        .append(field.getName())
                                        .append("\" = \'")
                                        .append(field.get(obj).toString())
                                        .append("\'");
                            }
                        }
                    }
                }

                return "UPDATE \"" +
                        clazz.getSimpleName() +
                        "\" SET " +
                        updatedValue +
                        " WHERE " +
                        condition +
                        ";";
            }
        }
        return null;
    }

    public static String delete(){
        return null;
    }
}
