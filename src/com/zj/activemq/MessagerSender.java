package com.zj.activemq;

public class MessagerSender {
	public static void main(String[] args) {
		MQProducer producer=new MQProducer();
		producer.sendMessage("hello", "你好");
	}
}
