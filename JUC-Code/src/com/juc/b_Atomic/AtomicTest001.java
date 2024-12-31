package com.juc.b_Atomic;

//原子变量范例
/*
1,i++的原理，java底层，先复制后自加1
     int i = 10;
	 i = i++;  等号前面的i值还是10
	 原因,底层源码，三步操作
	 int temp = i;  先赋值给一个临时变量
	 int i = i + 1;  然后等号后面的i再自加1
	 int i = temp;   这里是等号前面的i, 赋值temp,所以结果是10
2,下面分线程的sout语句有时会输出重复的数字，按说是一个线程赋值完毕后，另一个线程再获取值的话是不用的数
  原因就是第一条中的 java底层给变量serialNum赋值时是分三步的，一个线程还没走完三步，另一个就把serialNum
  的值取走了，因此重复，此变量操作没有原子性，不是不可分割的。
  给变量加volatile修饰也不管用，volatile只是把这三步操作从各线程的缓存移到了堆内存中而已。
3,在AtomicTest002中使用原子变量，可解决以上问题
*/
public class AtomicTest001 {

	public static void main(String[] args){
		MyRunnable mr = new MyRunnable();
		for(int i=0; i < 10; i++){  //创建10个线程
			new Thread(mr).start();
		}
	}

}


class MyRunnable implements Runnable {
	//实例变量在堆内存中，是多线程共享的
	//private int serialNum;
	private volatile int serialNum;  //加volatile修饰符也不管用
	 
	public void run(){
		
		try{
			Thread.sleep(300);  //每个线程休眠0.3秒，以此可明显暴露并测试出i++三步操作出现的问题
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		System.out.println("serialNum==>" + getSerialNum());    //这里有时会输出0-9中重复的数字，原因是上面的i++赋值是三步操作
	}															//注意这里不要直接输出变量：serialNum++，否则一致是0

	public int getSerialNum(){
		return serialNum++;
	}
}