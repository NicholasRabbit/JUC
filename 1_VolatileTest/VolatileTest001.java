public class VolatileTest001 {

	public static void main(String[] args){
	
		MyRunnable01 mr01 = new MyRunnable01();
		Thread t01 = new Thread(mr01);
		t01.setName("���߳�");
		t01.start();
	    /*1,���̵߳�flagʵ�����������ڶ��ڴ��У������ڴ����̹߳���
		    JVM��Ϊÿ���߳��ṩһ������Ļ���ռ䣬ÿ���߳�����ʱ��Ѷ��е�flag�õ��Լ��Ļ����У�������޸ĺ��ٷŻ�ȥ��
		  2,�ڴ�Ĳ��ɼ��ԣ����������߳̿��������̵߳Ļ���
		    ���ڷ��߳���˯��500���룬�����߳��ȴӶ��ڴ��л�ȡflag,ֵΪfalse,����ŵ��Լ������У�
			main���߳��У�whileѭ����java������ײ�ģ�ִ���ٶȷǳ��죬һֱ��ȡ�Լ��̻߳����е�ֵ��
			���߳̿��������߳����Ѿ��ڷ��̻߳����и�ֵ��flagΪtrue���ŵ��Ĺ�����ڴ��У������ڴ�Ĳ��ɼ��ԣ�����ִ�в���if��䡣
		  3,�����flag�������ˡ�volatile�����η��󣬸ı����Ͳ��ᱻ�߳��õ����ǵĻ����У������ڹ���Ķ��ڴ����޸ġ�
		    �����Ͳ��ᷢ�����������ˣ�
		  4,volatile��synchronized���η���ͬ����synchronized���ٶȿ�
		    sychronized���л����ԣ�volatile�����л�����
			volatile���ܱ�֤������ԭ����
		  5,��volatile���κ����������ˣ�����	
		*/	
		while(true){
			if(mr01.getFlag()){
				System.out.println(Thread.currentThread().getName() + "==>execute!");
				break;
			}
			//����ִ��sout�󷴶��������̻߳�ȡ���µ�flag���Ӷ���ֹѭ����ԭ����о���
			//System.out.println("main while loop");  
		}
	
	}
}


class MyRunnable01 implements Runnable {

	//��ʹ��volatile���Σ����̻߳�һֱִ��whileѭ������ִ�����ڵ�if���break
	private boolean flag = false;
	
	//ʹ��volatile���Σ����̻߳ῴ��flag���true�ˣ�������Լ����������е�flagֵ
	//private volatile boolean flag = false;
	
	public void run(){
		
		//���÷��߳�˯��200���룬���������߳��ȵõ�flag��ֵ
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