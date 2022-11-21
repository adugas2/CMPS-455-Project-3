package com.company;

import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        int quantum = 5;
        //FCFS();
        //NPSJF();
        //PSJF();
        RR(quantum);
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
    public static void RR(int quantum){
        Random r = new Random();
        // T
        int numThreads = r.nextInt(26) +1;

        //Semaphores
        Semaphore[] start = new Semaphore[numThreads];
        Arrays.fill(start, new Semaphore(0));
        Semaphore[] end = new Semaphore[numThreads];
        Arrays.fill(end, new Semaphore(0));
        Semaphore dispatcherStart = new Semaphore(1);
        Semaphore coreStart = new Semaphore(0);

        //threads
        int maxBurst = 0;
        int burst;
        MyThread[] taskThreads = new MyThread[numThreads];
        for (int i = 0; i < taskThreads.length; i++) {
            System.out.println("Main thread     | Creating process thread " + i);
            burst = r.nextInt(51)+1;
            maxBurst += burst;
            taskThreads[i] = new MyThread(i, burst, start, end);
        }
        System.out.println();
        System.out.println("--------------- Ready Queue ---------------");
        for (MyThread taskThread : taskThreads) {
            System.out.println("ID:"+ taskThread.id +", Max Burst:"+ taskThread.maxBurst +", Current Burst:0");
        }
        System.out.println("-------------------------------------------");
        System.out.println();
        System.out.println("Main thread     | Forking dispatcher");
        System.out.println();
        DispatcherThread dispatcher = new DispatcherThread(quantum, dispatcherStart, coreStart, taskThreads, maxBurst);
        CPU core = new CPU(taskThreads, start, end, dispatcherStart, coreStart, dispatcher, maxBurst);
        core.start();
        for (Thread taskThread : taskThreads) {
            taskThread.start();
        }
        dispatcher.start();



    }
    // End code changes by Ethan Forster
}
