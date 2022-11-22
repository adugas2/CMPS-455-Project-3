package com.company;

import java.util.concurrent.Semaphore;

public class DispatcherThread extends Thread {
    //Begin code changes by Ethan Forster
    public int allottedBurst;
    public int quantum;
    private Semaphore dispatcherStart;
    private Semaphore coreStart;
    public MyThread[] readyQueue;
    public int currentposition = 0;
    private int burstGoal;
    private int alg;

    public DispatcherThread(int quantum, Semaphore dispatcherStart, Semaphore coreStart, MyThread[] readyQueue, int burstGoal, int alg) {
        this.quantum = quantum;
        this.dispatcherStart = dispatcherStart;
        this.coreStart = coreStart;
        this.readyQueue = readyQueue;
        this.burstGoal = burstGoal;
        this.alg = alg;
    }

    public DispatcherThread(Semaphore dispatcherStart, Semaphore coreStart, MyThread[] readyQueue, int burstGoal, int alg) {
        this.dispatcherStart = dispatcherStart;
        this.coreStart = coreStart;
        this.readyQueue = readyQueue;
        this.burstGoal = burstGoal;
        this.alg = alg;
    }

    @Override
    public void run() {
        switch (alg) {
            case (2): {
                RR();
                break;
            }
            case (1): {
                FCFS();
                break;
            }
        }
    }

    public void RR() {
        System.out.println("Dispatcher    | Using CPU");
        System.out.println("Dispatcher    | Running RR algorithm, Time Quantum = " + quantum);
        System.out.println();
        while(burstGoal > 0) {
            try {
                dispatcherStart.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // select task from ready queue
            // assign Task and allotted burst to Core
            System.out.println("Dispatcher    | Running process " + currentposition);
            allottedBurst = quantum;

            if (readyQueue[currentposition].currentBurst >= allottedBurst) {
                burstGoal-=allottedBurst;
            } else {
                burstGoal-= readyQueue[currentposition].currentBurst;
            }


            coreStart.release();
            currentposition++;
            if (currentposition > readyQueue.length -1){
                currentposition = 0;
            }
        }
        //End code changes by Ethan Forster
    }
    // Start code changes by Brian Hodge
    public void FCFS() {
        System.out.println("Dispatcher    | Using CPU");
        System.out.println("Dispatcher    | Running FCFS Algorithm");
        System.out.println();
        while(burstGoal > 0) {
            try {
                dispatcherStart.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // select task from ready queue
            // assign Task and allotted burst to Core
            System.out.println("Dispatcher    | Running process " + currentposition);
            allottedBurst = burstGoal;
            burstGoal-= readyQueue[currentposition].currentBurst;

            coreStart.release();
            currentposition++;
            if (currentposition > readyQueue.length -1){
                currentposition = 0;
            }
        }
        //End code changes by Brian Hodge
    }
}
