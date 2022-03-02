

import java.util.concurrent.*;
import java.util.*;


/*
复习创建线程的第三种方式
FutureTask可用于闭锁，因为它的方法get()不获取到返回值的话当前线程阻塞
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
			Integer count = ft.get();  
			Integer count2 = ft2.get();  //调用get()方法造成当前线程阻塞，下面的sout等到这句执行完才会执行
			Integer sum = count + count2;
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