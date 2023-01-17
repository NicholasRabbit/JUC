package com.juc.b_Atomic;

import java.util.concurrent.atomic.AtomicInteger;

/*ʹ��ԭ�ӱ������������߳�ʹ�ö��ڴ��еı���ʱ���������ظ�����
  ������ʽ�ڵײ���õ���CAS(Compare And Swap)�㷨
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
	
	private AtomicInteger serialNum = new AtomicInteger(0);  //��ʼ������Ϊ0
	
	public void run(){
		
		try{
		    Thread.sleep(300);           //˯���߳�0.3�룬ʹ��i++����������ױ�¶
		}catch(InterruptedException e){
		    e.printStackTrace();
		}
		
		System.out.println("serialNum==>" + getSerialNum());
	}

	public int getSerialNum(){
		return serialNum.getAndIncrement();  //getAndIncrement()�����൱��i++������ֵ��int����
	}
}