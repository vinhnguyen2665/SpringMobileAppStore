package com.vinhnq.beans;

public class test {
    public static void main(String[] args) {
        Version version = new Version("1.0");
        Version version2 = new Version("0.1");
        System.out.println(version.compareTo(version2));
    }
}
