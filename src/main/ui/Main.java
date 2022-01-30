package ui;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        List<List<Object>> t  = new ArrayList<>();
        List<Object> t0 = new ArrayList<>();
        t0.add("Philosophy");
        t0.add(3);

        List<Object> t1 = new ArrayList<>();
        t1.add("Math");
        t1.add(4);

        t.add(t0);
        t.add(t1);

        System.out.print(t);
        System.out.print(t.toString().contains("[Math, 3]"));

    }


}
