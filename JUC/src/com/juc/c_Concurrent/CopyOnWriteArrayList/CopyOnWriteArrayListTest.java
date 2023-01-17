package com.juc.c_Concurrent.CopyOnWriteArrayList;

import java.util.*;
import java.util.concurrent.*;

/*
CopyOnWriteArrayList范例
1，一般链表即使通过使用 Collections.synchronizedList(..)方法加了同步锁，
   当边读边添加元素时，会报：ConcurrentModificationException
2，可使用CopyOnWriteArrayList，它解决了链表边读边复制时出现的
   但是，它底层的原理是在添加的时候把旧的链表重新复制了一次，因此如果添加操作较多的话使用它会造成消耗资源过大。

3，CopyOnWriteArrayList也可用于边遍历边删除的操作，不会报错
*/


public class CopyOnWriteArrayListTest {

	public static void main(String[] args){
		Runnable mr = new MyRunnable();
		Runnable mr2 = new MyRunnable2();
		/* //这里报错
		for(int i = 0; i < 10; i++){
			Thread t = new Thread(mr);
			t.start();
		}
		*/

		//使用CopyOnWriteArrayList，不报错
		for(int i = 0; i < 10; i++){
			new Thread(mr2).start();
		}
	}

}


class MyRunnable implements Runnable {

	private static List<String> list = Collections.synchronizedList(new ArrayList<String>());  //创建一个加同步锁的链表
	
	static{
		list.add("a");
		list.add("b");
		list.add("c");
	}
	
	public void run(){
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
			//list.add("ABC");  //这里遍历的时候添加元素会报错
			
		}
	}
}

//使用CopyOnWriteArrayList
class MyRunnable2 implements Runnable {
	
	private static CopyOnWriteArrayList<String>  list = new CopyOnWriteArrayList<>();

	static{
		list.add("A");
		list.add("B");
		list.add("C");
	}
	
	public void run(){
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			System.out.println("CopyOnWrite==>" + it.next());
			list.add("XX");
		}
		//边遍历读取，边删除元素
		for(String s : list){
			if("A".equals(s)){
				list.remove(s);
			}
		}
		System.out.println("===============删除A后");
		//删除元素后，要重新获取迭代器
		Iterator<String> it2 = list.iterator();
		while(it.hasNext()){
			System.out.println("list==>" + it.next());
		}

	}

}

