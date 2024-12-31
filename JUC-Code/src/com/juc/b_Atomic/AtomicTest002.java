package com.juc.b_Atomic;

import java.util.concurrent.atomic.AtomicInteger;

/*使用原子变量，避免多个线程使用堆内存中的变量时出现数据重复错误
  这种形式在底层就用到了CAS(Compare And Swap)算法
*/
public class AtomicTest002 {
	
	public static void main(String[] args){
		 
		 Runnable mr02 = new MyRunnable02();
		 for(int i = 0; i < 10; i++){
			new Thread(mr02).start();
		 }

	}
}


class MyRunnable02 implements Runnable {
	
	private AtomicInteger serialNum = new AtomicInteger(0);  //初始化变量为0
	
	public void run(){
		
		try{
		    Thread.sleep(300);           //睡眠线程0.3秒，使得i++的问题更容易暴露
		}catch(InterruptedException e){
		    e.printStackTrace();
		}
		
		System.out.println("serialNum==>" + getSerialNum());
	}

	public int getSerialNum(){
		return serialNum.getAndIncrement();  //getAndIncrement()方法相当于i++，返回值是int类型
	}
}