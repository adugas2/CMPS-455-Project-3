package com.company;

import java.util.concurrent.Semaphore;

public class CPU extends Thread {
    //Begin code changes by Ethan Forster
    private int taskID;
    public MyThread[] readyQueue;
    private Semaphore[] taskStart;
    private Semaphore[] taskEnd;
    private Semaphore dispatcherStart;
    private Semaphore coreStart;
    private DispatcherThread dispatcher;
    private int burstGoal;

    public CPU(MyThread[] readyQueue, Semaphore[] taskStart, Semaphore[] taskEnd, Semaphore dispatcherStart,
               Semaphore coreStart, DispatcherThread dispatcher, int burstGoal){
        this.readyQueue = readyQueue;
        this.taskStart = taskStart;
        this.taskEnd = taskEnd;
        this.dispatcherStart = dispatcherStart;
        this.coreStart = coreStart;
        this.dispatcher = dispatcher;
        this.burstGoal = burstGoal;
    }


    @Override
    public void run() {
        while(burstGoal > 0) {
            try {
                coreStart.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // update assigned Task’s allotted burst
            taskID = dispatcher.currentposition;
            readyQueue[taskID].allottedBurst = dispatcher.allottedBurst;
            burstGoal -= dispatcher.allottedBurst;

            taskStart[taskID].release();

            try {
                taskEnd[taskID].acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            dispatcherStart.release();
        }
    }
    //End code changes by Ethan Forster
}
