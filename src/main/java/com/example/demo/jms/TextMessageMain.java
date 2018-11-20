package com.example.demo.jms;

import javax.jms.JMSException;
import javax.naming.NamingException;

import com.jms.textMessage.topic.TextMessageListener;
import com.jms.textMessage.topic.TextMessageProducer;

public class TextMessageMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			TextMessageListener listener=new TextMessageListener();
			TextMessageProducer producer=new TextMessageProducer();
			for(int i=0;i<10;i++) {
				producer.sendTextMessage("Message==>"+i);
			}
			
			
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
