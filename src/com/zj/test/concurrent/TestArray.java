package com.zj.test.concurrent;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class TestArray {
	class Demo{
		public volatile int val=0;
		volatile int age=0;
		protected volatile int val1=0;
		private volatile int age1=0;
	}
	AtomicIntegerFieldUpdater<Demo>   getUpdater(String fieldName){
		return AtomicIntegerFieldUpdater.newUpdater(Demo.class, fieldName);
	}
	public void test(){
		Demo demo=new Demo();
		System.out.println(getUpdater("val").addAndGet(demo, 1));
		System.out.println(getUpdater("age").decrementAndGet(demo));
//		System.out.println(getUpdater("val1").addAndGet(demo, 1));
//		System.out.println(getUpdater("age1").decrementAndGet(demo));
	}
	public static void main(String[] args) {
		TestArray t=new TestArray();
		t.test();
	}

}
