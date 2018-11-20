package com.jms.textMessage.queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class QueueTextMessageSender {
	
	 private QueueConnectionFactory queueConnectionFactory;
	 private QueueSession queueSession;
	 private String queueName = "spring_jms_queue_destination";
	 private String queueConnectionFactoryName = "queue_connection_factory";
	 private Queue queue;
	 private QueueConnection queueConnection;
	 private QueueSender queueSender;
	 
	 public QueueTextMessageSender() 
		       throws NamingException,JMSException{

		       InitialContext ctx = new InitialContext();

		       // Step1: Lookup the Connection Factory and the Topic
		       queueConnectionFactory = (QueueConnectionFactory)
		                        ctx.lookup(queueConnectionFactoryName);
		       queue = (Queue)ctx.lookup(queueName);

		       // Step2: Create a connection using the Factory
		       queueConnection = queueConnectionFactory.createQueueConnection();

		       // Step3: Create Topic Sessions using the connection
		       queueSession = queueConnection.createQueueSession
		                         (false,Session.AUTO_ACKNOWLEDGE);

		       // Step4: Create TopicPublisher
		       queueSender = queueSession.createSender(queue);

		       queueConnection.start();
		}
	 
	 public void close() throws JMSException{
		 queueConnection.stop();
	 }

	 public void sendTextMessage(String msg) throws JMSException {
	       //  Creating a Text Message with the String object
	       TextMessage txtMsg = queueSession.createTextMessage(msg);

	       //  Publishing the message object to the Topic
	       queueSender.send(txtMsg);
	 }
	 
	 public static void main(String[] args) throws IOException {
			// TODO Auto-generated method stub
			
			try {
				QueueTextMessageSender sender=new QueueTextMessageSender();
				System.out.println("Now write somethings /n");
			      BufferedReader br = new BufferedReader
			                         (new InputStreamReader(System.in));
			      while(true) {
			    	  String msg = br.readLine();
			           if(msg.equalsIgnoreCase("exit")) {
			        	   sender.sendTextMessage(msg);
			        	   sender.close();
			                 System.exit(0);
			           } else {
			        	   sender.sendTextMessage(msg);
			           }
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
