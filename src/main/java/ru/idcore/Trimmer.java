package ru.idcore;

public class Trimmer {
    public static String extraTrimString(String s) {
        return s.trim().replaceAll("\\s+", " ");
    }
}
