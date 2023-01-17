package com.juc.b_Atomic;

//ԭ�ӱ�������
/*
1,i++��ԭ��java�ײ㣬�ȸ��ƺ��Լ�1
     int i = 10;
	 i = i++;  �Ⱥ�ǰ���iֵ����10
	 ԭ��,�ײ�Դ�룬��������
	 int temp = i;  �ȸ�ֵ��һ����ʱ����
	 int i = i + 1;  Ȼ��Ⱥź����i���Լ�1
	 int i = temp;   �����ǵȺ�ǰ���i, ��ֵtemp,���Խ����10
2,������̵߳�sout�����ʱ������ظ������֣���˵��һ���̸߳�ֵ��Ϻ���һ���߳��ٻ�ȡֵ�Ļ��ǲ��õ���
  ԭ����ǵ�һ���е� java�ײ������serialNum��ֵʱ�Ƿ������ģ�һ���̻߳�û������������һ���Ͱ�serialNum
  ��ֵȡ���ˣ�����ظ����˱�������û��ԭ���ԣ����ǲ��ɷָ�ġ�
  ��������volatile����Ҳ�����ã�volatileֻ�ǰ������������Ӹ��̵߳Ļ����Ƶ��˶��ڴ��ж��ѡ�
3,��AtomicTest002��ʹ��ԭ�ӱ������ɽ����������
*/
public class AtomicTest001 {

	public static void main(String[] args){
		MyRunnable mr = new MyRunnable();
		for(int i=0; i < 100; i++){  //����10���߳�
			new Thread(mr).start();
		}
	}

}


class MyRunnable implements Runnable {
	//ʵ�������ڶ��ڴ��У��Ƕ��̹߳����
	//private int serialNum;
	private volatile int serialNum;  //��volatile���η�Ҳ������
	 
	public void run(){
		
		try{
			Thread.sleep(500);  //ÿ���߳�����0.3�룬�Դ˿����Ա�¶�����Գ�i++�����������ֵ�����
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		
		System.out.println("serialNum==>" + getSerialNum());    //������ʱ�����0-9���ظ������֣�ԭ���������i++��ֵ����������
	}															//ע�����ﲻҪֱ�����������serialNum++������һ����0

	public int getSerialNum(){
		return serialNum++;
	}
}