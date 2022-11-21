package com.company;

import java.util.concurrent.Semaphore;

public class DispatcherThread extends Thread {

    public int allottedBurst;
    private Semaphore dispatcherStart;
    private Semaphore coreStart;
    public Thread[] readyQueue;

    public DispatcherThread() {

    }

    @Override
    public void run() {
        try {
            dispatcherStart.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // select task from ready queue

        // assign Task and allotted burst to Core

        coreStart.release();

    }
}
