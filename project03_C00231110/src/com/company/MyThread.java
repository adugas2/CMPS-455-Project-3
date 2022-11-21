package com.company;

import java.util.concurrent.Semaphore;

public class MyThread extends Thread{

    private int id;
    private int maxBurst;
    private int currentBurst;
    private Semaphore[] taskStart;
    private Semaphore[] taskEnd;

    public MyThread(int id, int burst, Semaphore[] start, Semaphore[] end){
        super(String.valueOf(id));
        this.id = id;
        maxBurst = burst;
        currentBurst = burst;
        taskEnd = end;
        taskStart = start;
    }

    @Override
    public void run() {
        //wait to start task
        while (currentBurst > 0){
            try {
                taskStart[id].acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // loop for allotted # of bursts

            //notify CPU of finished task
            taskEnd[id].release();

        }
    }
}
