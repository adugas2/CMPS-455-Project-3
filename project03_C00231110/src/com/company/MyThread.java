package com.company;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThread extends Thread{
    //Begin code changes by Ethan Forster
    public int id;
    public int maxBurst;
    public int currentBurst;
    public int allottedBurst;
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
            System.out.println("Proc. Thread "+id+"  | On CPU: MB="+maxBurst+", CB="+currentBurst+", BT=" + allottedBurst);
            // loop for allotted # of bursts
            for (int i = 0; i < allottedBurst; i++){
                System.out.println("Proc. Thread "+id+"  | Using CPU; On burst "+i+".");
                currentBurst--;
            }
            System.out.println();
            //notify CPU of finished task
            taskEnd[id].release();

        }
    }
    //End code changes by Ethan Forster
}
