package com.company;

import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        //FCFS();
        //NPSJF();
        //PSJF();
        RR();
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

    // Begin code changes by Ethan Forster
    public static void RR(){
        Random r = new Random();
        // T
        int numThreads = r.nextInt(26) +1;

        //Semaphores
        Semaphore[] start = new Semaphore[numThreads];
        Arrays.fill(start, new Semaphore(0));
        Semaphore[] end = new Semaphore[numThreads];
        Arrays.fill(end, new Semaphore(0));

        //threads
        Thread[] taskThreads = new Thread[numThreads];
        for (int i = 0; i < taskThreads.length; i++) {
            System.out.println("Main thread     | Creating process thread " + i);
            taskThreads[i] = new MyThread(i, r.nextInt(51)+1, start, end);
        }
        for (Thread taskThread : taskThreads) {
            taskThread.start();
        }

    }
    // End code changes by Ethan Forster
}
