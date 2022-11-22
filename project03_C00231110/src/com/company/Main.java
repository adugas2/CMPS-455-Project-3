package com.company;

import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {

        // Begin code changes by Dalton Thayer
        int algo = 0;
        int coreNum = 0;
        int quantum = 0;
        Random random = new Random();

        try {
            try {
                if (args[0].contains("-S")) {
                    switch (Integer.parseInt(args[1])) {
                        case 1:
                            algo = 1;
                            break;
                        case 2:
                            algo = 2;
                            break;
                        case 3:
                            algo = 3;
                            break;
                        case 4:
                            algo = 4;
                            break;

                    }
                    if (args[1].contains("2")) {
                        quantum = Integer.parseInt(args[2]);
                        if (args[3].contains("-C")) {
                            switch (Integer.parseInt(args[4])) {
                                case 1:
                                    coreNum = 1;
                                    break;
                                case 2:
                                    coreNum = 2;
                                    break;
                                case 3:
                                    coreNum = 3;
                                    break;
                                case 4:
                                    coreNum = 4;
                                    break;
                            }
                        }
                    } else if (args[2].contains("-C")) {
                        switch (Integer.parseInt(args[3])) {
                            case 1:
                                coreNum = 1;
                                break;
                            case 2:
                                coreNum = 2;
                                break;
                            case 3:
                                coreNum = 3;
                                break;
                            case 4:
                                coreNum = 4;
                                break;
                        }
                    }

                } else if (args[0].contains("-C")) {
                    switch (Integer.parseInt(args[1])) {
                        case 1:
                            coreNum = 1;
                            break;
                        case 2:
                            coreNum = 2;
                            break;
                        case 3:
                            coreNum = 3;
                            break;
                        case 4:
                            coreNum = 4;
                            break;
                    }
                    if (args[2].contains("-S")) {
                        switch (Integer.parseInt(args[1])) {
                            case 1:
                                algo = 1;
                                break;
                            case 2:
                                algo = 2;
                                break;
                            case 3:
                                algo = 3;
                                break;
                            case 4:
                                algo = 4;
                                break;

                        }
                        if (args[3].contains("2")) {
                            quantum = Integer.parseInt(args[4]);
                        }
                    }
                }
            } catch (NumberFormatException ex) {
            }

            while (true) {
                Scanner in = new Scanner(System.in);
                int loopControl = 0;

                if ((algo > 0 && algo < 5) && (coreNum > 0 && coreNum < 5)) {
                    loopControl = 1;
                }

                if (loopControl == 1) {
                    break;
                } else {
                    System.out.println("Invalid Input");
                }
                while (true) {
                    System.out.println("1. First Come, First Serve");
                    System.out.println("2. Round Robin");
                    System.out.println("3. Non-Preemptive SJF");
                    System.out.println("4. Preemptive SJF");
                    System.out.println("Enter Algorithm you would like to run: ");
                    try {
                        algo = Integer.parseInt(in.nextLine());
                    } catch (NumberFormatException ex) {
                    }
                    if (algo == 2) {
                        while (true) {
                            System.out.println("Round robin detected. What would you like time quantum to be? (2-10)");
                            try {
                                quantum = Integer.parseInt(in.nextLine());
                            } catch (NumberFormatException ex) {
                            }
                            if (quantum < 11 && quantum > 1) {
                                break;
                            } else {
                                System.out.println("Invalid Input.");
                            }
                        }
                    }
                    if (algo > 0 && algo < 5) {
                        break;
                    }
                }
                while (true) {
                    System.out.println("Enter Number of cores you would like to run with: (1-4) ");
                    try {
                        coreNum = Integer.parseInt(in.nextLine());
                    } catch (NumberFormatException ex) {
                    }
                    if (coreNum < 5 && coreNum > 0) {
                        break;
                    } else {
                        System.out.println("Invalid Input.");
                    }
                }
            }
            /*                                                          Testing and Examples
            System.out.println(Arrays.toString(args));
            System.out.println(algo);
            System.out.println(coreNum);
            System.out.println(quantum);

            MyThread[] threads = new MyThread[random.nextInt(25)];
            for (int i = 0; i < threads.length; i++){
                MyThread thread = new MyThread(i, random.nextInt(50), 'semaphore list start', 'semaphore list end');
                threads[i] = thread;
            }
            */

            switch (algo){
                case 1:
                    FCFS();
                    break;
                case 2:
                    RR(quantum);
                    break;
                case 3:
                    //NSJF();
                    break;
                case 4:
                    //PSJF();
                    break;
            }
        }
        catch(IndexOutOfBoundsException ex){}

    }
    // End code changes by Dalton Thayer

    // Begin code changes by Austin Dugas
    public static void NPSJF(){
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
        DispatcherThread dispatcher = new DispatcherThread(dispatcherStart, coreStart, taskThreads, maxBurst, 3);
        CPU core = new CPU(taskThreads, start, end, dispatcherStart, coreStart, dispatcher, maxBurst);
        core.start();
        for (Thread taskThread : taskThreads) {
            taskThread.start();
        }
        dispatcher.start();
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
        DispatcherThread dispatcher = new DispatcherThread(quantum, dispatcherStart, coreStart, taskThreads, maxBurst, 2);
        CPU core = new CPU(taskThreads, start, end, dispatcherStart, coreStart, dispatcher, maxBurst);
        core.start();
        for (Thread taskThread : taskThreads) {
            taskThread.start();
        }
        dispatcher.start();



    }
    // End code changes by Ethan Forster
    //Start code changes by Brian Hodge
    public static void FCFS() {
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
        DispatcherThread dispatcher = new DispatcherThread(dispatcherStart, coreStart, taskThreads, maxBurst, 1);
        CPU core = new CPU(taskThreads, start, end, dispatcherStart, coreStart, dispatcher, maxBurst);
        core.start();
        for (Thread taskThread : taskThreads) {
            taskThread.start();
        }
        dispatcher.start();
        //End code changes by brian Hodge
    }
}
