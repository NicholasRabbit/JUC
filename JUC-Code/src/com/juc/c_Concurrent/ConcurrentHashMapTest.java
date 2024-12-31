package com.juc.c_Concurrent;

import java.util.concurrent.*;

/*
1, ConcurrentHashMap代码范例，与HashMap用法一样，方法名一样，只是线程安全了，高并发下常用。
2, JDK6之前采用所分段机制保证线程安全，类似于InnoDB引擎的做法
3, JDK6以后采用CAS算法来保证线程安全。
*/

public class ConcurrentHashMapTest {

	public static void main(String[] args){
		ConHashMap chm = new ConHashMap();
		chm.setCon(new ConcurrentHashMap<String,String>());
		for(int i=0; i<100; i++){
			ConcurrentHashMap<String,String> con = chm.getCon();
			Runnable r = new MyThread(con);
			Thread t = new Thread(r);
			t.start();
		}


	}
}

class MyThread implements Runnable {
	
	private ConcurrentHashMap<String,String> con;

	public void run(){
		con.put("k1","v1");	
		con.put("k2","v2");
		con.put("k3","v3");
		System.out.println("map==>" + con);
	}

	public MyThread(){
	
	}
	public MyThread(ConcurrentHashMap<String,String> con){
		this.con = con;
	}
}

class ConHashMap {

	private ConcurrentHashMap<String,String>  con;

	public ConcurrentHashMap<String,String> getCon(){
		return this.con;
	}

	public void setCon(ConcurrentHashMap<String,String> con){
		this.con = con;
	}

	
}


