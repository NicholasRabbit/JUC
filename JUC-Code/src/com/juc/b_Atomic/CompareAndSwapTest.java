package com.juc.b_Atomic;

import java.util.Random;

/*模拟CAS算法
  使用原子变量时，java底层就是使用的CAS算法保证事务的,
  这种算法在高并发时处理速度较快，因为是在最底层。

  思路：
  1，先获取变量当前的值作为旧值，即赋新值时用来比较的期望值
  2，给这个变量赋值时再次获取它现在的值，与期望值作比较，看是否相同，相同则赋予新值，否则再重复1步骤。
*/



public class CompareAndSwapTest{

	public static void main(String[] args){
	
		CompareAndSwap  cas = new CompareAndSwap();

		//创建10个线程对value进行赋值，测试CAS算法能否保证事务
		for(int i=0; i<100; i++){
			Thread t = new Thread(new Runnable(){
				public void run(){
					int oldValue = cas.getValue();  //获取旧址，即赋新值前的期望值
					Random r = new Random();
					boolean success = cas.compareAndSet(oldValue, r.nextInt(101));
					System.out.println(success +" : "+cas.getValue() );
				}
			});

			t.start();
		}
	
	}
}





class CompareAndSwap {

	private int value;

	//这几个方法都要加synchronized修饰，默认锁住的是this，保证线程安全，防止别的线程调用
	public synchronized int getValue(){
		return this.value;
	}
	
	//在这里进行比较和替换
	public synchronized int compareAndSwap(int expectedValue, int newValue){
		int oldValue = value;  //获取当前时间点value的值,与期望值作比较，如果相同则赋予新值。
		if(oldValue == expectedValue){  
			this.value = newValue;
		}
		return oldValue;
	}

	
	public synchronized boolean compareAndSet(int expectedValue, int newValue){
	
		return expectedValue == compareAndSwap(expectedValue, newValue);  //返回true则说明当前value与期望值相同，修改成功，否则修改失败。
	}

}
