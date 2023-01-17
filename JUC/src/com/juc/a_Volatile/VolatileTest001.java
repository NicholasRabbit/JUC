package com.juc.a_Volatile;

public class VolatileTest001 {

	public static void main(String[] args){
	
		MyRunnable01 mr01 = new MyRunnable01();
		Thread t01 = new Thread(mr01);
		t01.setName("分线程");
		t01.start();
	    /*1,分线程的flag实例变量，存在堆内存中，而堆内存多个线程共享；
		    JVM会为每个线程提供一块独立的缓存空间，每个线程运行时会把堆中的flag拿到自己的缓存中，用完或修改后再放回去；
		  2,内存的不可见性，本例中主线程看不到分线程的缓存
		    由于分线程先睡眠500毫秒，故主线程先从堆内存中获取flag,值为false,而后放到自己缓存中，
			main主线程中，while循环在java中是最底层的，执行速度非常快，一直读取自己线程缓存中的值，
			主线程看不到分线程中已经在分线程缓存中赋值给flag为true并放到的共享堆内存中，即是内存的不可见性，所以执行不了if语句。
		  3,如果给flag变量加了“volatile”修饰符后，改变量就不会被线程拿到它们的缓存中，而是在共享的堆内存中修改‘
		    这样就不会发生上面的情况了；
		  4,volatile与synchronized修饰符不同，比synchronized的速度快
		    sychronized具有互斥性，volatile不具有互斥性
			volatile不能保证变量的原子性
		  5,被volatile修饰后不能重排序了？？？	
		*/	
		while(true){
			if(mr01.getFlag()){
				System.out.println(Thread.currentThread().getName() + "==>execute!");
				break;
			}
			//这里执行sout后反而会让主线程获取最新的flag，从而终止循环，原因待研究。
			//System.out.println("main while loop");  
		}
	
	}
}


class MyRunnable01 implements Runnable {

	//不使用volatile修饰，主线程会一直执行while循环，不执行其内的if语句break
	private boolean flag = false;
	
	//使用volatile修饰，主线程会看到flag变成true了，会更新自己工作缓存中的flag值
	//private volatile boolean flag = false;
	
	public void run(){
		
		//先让分线程睡眠200毫秒，意在让主线程先得到flag的值
		try{
			Thread.sleep(200);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		this.flag = true;
		System.out.println(Thread.currentThread().getName() + ":flag ==> " + flag);
	}

	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	public boolean getFlag(){
		return this.flag;
	}

}