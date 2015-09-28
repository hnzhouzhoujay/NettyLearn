package com.zj.test.concurrent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class TestAutom {
	static AtomicInteger val=new AtomicInteger(0);
	static AtomicInteger val1=new AtomicInteger(0);
	static int regularVal=0;
	static int regularVal1=0;
	static boolean flag=true;
		public static void main(String[] args) {
			ExecutorService exe=Executors.newFixedThreadPool(5);
			for(int i=0;i<5;i++)
			exe.execute(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while(flag){
						val.incrementAndGet();
						regularVal++;
						val1.incrementAndGet();
						regularVal1++;
					}
				}
				
			});
			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flag=false;
			System.out.println("concurrent val:"+val.get());
			System.out.println("regular val:"+regularVal);
			System.out.println("concurrent val:"+val1.get());
			System.out.println("regular val:"+regularVal1);
			
		}

}
