package com.juc.g_ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTest {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);



    }

}

class ThreadPoolDemo implements Runnable {


    @Override
    public void run() {
        
    }
}
