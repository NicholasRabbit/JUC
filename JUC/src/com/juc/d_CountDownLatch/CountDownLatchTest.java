package com.juc.d_CountDownLatch;

import java.util.concurrent.CountDownLatch;


/*
1,CountDownLatch.java用法范例：
  作用：这个类用来等待所有线程执行完以后，再执行一个方法，例如用来统计所有线程执行完的耗时
2,注意latch属性是实例变量，用到它的方法要加同步锁，保证线程安全；
*/
public class CountDownLatchTest {

	public static void main(String[] args){
		
		//一，初始化CountDownLatch对象，并设定好监控线程的数量，latch.countDown()方法每执行一次，则此数值减一，
		//    当减为0时，才会执行latch.await()方法
		final CountDownLatch latch = new CountDownLatch(10);   //引用变量加final修饰，表示这个引用不再指向其它对象
		
		//二，向分线程类里传CountDownLatch对象。
		Runnable mr = new MyRunnableDemo(latch);
	
		long startTime = System.currentTimeMillis();	//开始时间

		//三，开启10个线程，与上面的“10”数量一致
		for(int i = 0; i < 10; i++){
			Thread t = new Thread(mr);
			t.start();
		}
		
		//四，调用await()方法，此方法执行前，当前线程一直处于阻塞状态，目的为等10个线程运行完
		try {
			latch.await();	
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
		//五，计算用时
		long endTime = System.currentTimeMillis();
		long usedTime = endTime - startTime;
		System.out.println("总用时==>" + usedTime);
		
	}
}



class MyRunnableDemo implements Runnable {
	
	private CountDownLatch latch;
	
	public MyRunnableDemo(){
	
	}
	public MyRunnableDemo(CountDownLatch latch){
		this.latch = latch;
	}

	public void run(){
		//六，为了保证线程安全，使用同步代码块
		synchronized (this) {  //这里也可以锁住“latch”
			
			//七，同时为了防止异常造成countDown()方法不执行，导致主线程的"10"无法归零，因此把该方法放到finally里执行
			//用finally必须用try
			try {
				//计算一万内的偶数
				for(int i = 0; i < 10000; i++){
					if(i % 2 == 0){
						System.out.println(i);
					}
				}	
			} finally {
				//八，调用countDown()方法，每执行一次，count数值减一
				latch.countDown();  
			}			
		}
		
	}
}