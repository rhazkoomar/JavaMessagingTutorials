package com.example.demo.jms;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jms.textMessage.topic.TextMessageProducer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
        Properties props = new Properties();
        props.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
        props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
        props.setProperty("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
        props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
        props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
 
        try {
            Context context = new InitialContext(props);
            System.out.println(context);
            System.out.println(context.lookup("queue_connection_factory"));
            System.out.println(context.lookup("jms/__defaultConnectionFactory"));
        } catch (NamingException e) {
            e.printStackTrace();
        }
		
		
		
		try {
			System.out.println("1==>");
			ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("context.xml");
			System.out.println("2==>");
			TextMessageProducer textMessageProducer= context.getBean("textMessageProducer", TextMessageProducer.class);
			System.out.println("textMessageProducer==>"+textMessageProducer);
			for(int i=0;i<10;i++) {
//				textMessageProducer.sendMessage("message=>"+i);
				
			}
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("exception==>"+e.getMessage());
		}

	}

}
