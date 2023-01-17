package com.juc.f_Lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/*
JUC中的线程锁的范例，即访问公共对象时，一个线程带一个锁访问这个对象，防止别的线程访问
ReentrantLock.java中的lock()方法与synchronized作用相同，都是为了保证线程安全，但是这个lock()方法的功能更强大
*/

public class ReentrantLockTest {

	public static void main(String[] args){
		Runnable seller = new TicketSeller();	
		for(int i = 0; i < 5; i++){
			Thread t = new Thread(seller);
			t.start();
		}
	}
}

class TicketSeller implements Runnable {
	
	private int ticketNum = 100;
	private Lock lock = new ReentrantLock();  

	public void run(){
		while (true) {
			
			
			//线程给当前使用的公用数据上锁
			lock.lock();

			try{
				
				if(ticketNum > 0){
					try{
						Thread.sleep(100);  //每个线程睡0.3秒，看是否有别的线程操作共有对象ticketNum
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + " 完成售票一张，余票为 ==> " + --ticketNum);
				} else {
					System.out.println("票已售完！");
					break;
				}
			}finally{
				//上完锁之后，一定要记得解锁，且放到finally语句里执行，保证一定解锁，否则一直占用公共数据，造成别的线程阻塞
				lock.unlock();
			}
		}	
	
	}
}