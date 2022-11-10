package com.company;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        //FCFS();
        NPSJF();
        //PSJF();
        //RR();
    }

    // Begin code changes by Austin Dugas
    public static void NPSJF(){
        // Display selected algorithm
        System.out.println("Scheduler Algorithm Select: Non-Preemptive Shortest Job First");
        // Initialize random variable
        Random r = new Random();
        // Establish number of threads [1, 25]
        int threadCount = r.nextInt(25);
    }
    // End code changes by Austin Dugas
}
