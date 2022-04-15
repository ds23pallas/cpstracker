package com.ds23pallas.cpstracker;

import java.util.LinkedList;
import java.util.Queue;

public class ClickTracker {

    private final Queue<Long> clicks = new LinkedList<>();

    public void addClick() {
        clicks.add(System.currentTimeMillis() + 1000L);
    }

    public int getCount() {
        long time = System.currentTimeMillis();

        while (!clicks.isEmpty() && clicks.peek() < time) {
            clicks.remove();
        }
        return clicks.size();
    }

}
