package com.company;

import java.util.concurrent.Semaphore;

public class CPU extends Thread {

    private int taskID;
    public Thread[] readyQueue;
    private Semaphore[] taskStart;
    private Semaphore[] taskEnd;
    private Semaphore dispatcherStart;
    private Semaphore coreStart;

    public CPU(){

    }

    @Override
    public void run() {
        try {
            coreStart.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // update assigned Tasks’s allotted burst

        taskStart[taskID].release();

        try {
            taskEnd[taskID].acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        dispatcherStart.release();
    }
}
