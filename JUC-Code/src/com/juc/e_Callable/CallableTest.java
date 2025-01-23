package com.juc.e_Callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/*
Reviewing the third approach to create a thread in Java.
FutureTask could be used for deadlock since the thread will be held if there is not any
returned value of the method 'get()';

*/

public class CallableTest {

	public static void main(String[] args){
		Callable<Integer> mc = new MyCallable();
		FutureTask<Integer> ft = new FutureTask<Integer>(mc);
		Thread t = new Thread(ft);
		
		Callable<Integer> mc2 = new MyCallable();
		FutureTask<Integer> ft2 = new FutureTask<Integer>(mc2);
		Thread t2 = new Thread(ft2);

		try{
			t.start();	
			t2.start();

			Integer count1 = ft.get();
			System.out.println("count==>" + count1);

			Integer count2 = ft2.get();  //调用get()方法造成当前线程阻塞，下面的sout等到这句执行完才会执行
			System.out.println("count2==>" + count2);

			Integer sum = count1 + count2;
			System.out.println("sum ==>" + sum);
			System.out.println("========================");   
		}catch (IllegalThreadStateException e){
			e.printStackTrace();
		}catch (InterruptedException e){
			e.printStackTrace();
		}catch (ExecutionException e){
			e.printStackTrace();
		}

	}

}


class MyCallable implements Callable<Integer> {

	public Integer call(){
		int count = 0;
		for(int i = 0; i < 100000; i++){
			count += i;
		}
		return count;
	}
}