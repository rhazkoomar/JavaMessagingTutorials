package com.jms.textMessage.topic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class TextMessageProducer {
	
	 private TopicConnectionFactory topicConnectionFactory;
	 private TopicSession topicSession;
	 private String topicName = "jms/MyTopic";
	 private String topicConnectionFactoryName = "jms/MyConnectionFactory";
	 private Topic topic;
	 private TopicConnection topicConnection;
	 private TopicPublisher topicPublisher;
	 
	 public TextMessageProducer() 
		       throws NamingException,JMSException{
		       InitialContext ctx = new InitialContext();
		       // Step1: Lookup the Connection Factory and the Topic
		       topicConnectionFactory = (TopicConnectionFactory)
		                        ctx.lookup(topicConnectionFactoryName);
		       topic = (Topic)ctx.lookup(topicName);
		       // Step2: Create a connection using the Factory
		       topicConnection = topicConnectionFactory.createTopicConnection();
		       // Step3: Create Topic Sessions using the connection
		       topicSession = topicConnection.createTopicSession
		                         (false,Session.AUTO_ACKNOWLEDGE);
		       // Step4: Create TopicPublisher
		       topicPublisher = topicSession.createPublisher(topic);
		       topicConnection.start();
		}
	 
	 public void close() throws JMSException{
		 topicConnection.stop();
	 }

	 public void sendTextMessage(String msg) throws JMSException {
	       //  Creating a Text Message with the String object
	       TextMessage txtMsg = topicSession.createTextMessage(msg);
	       //  Publishing the message object to the Topic
	       topicPublisher.publish(txtMsg);
	 }
	 
	 public static void main(String[] args) throws IOException {
			// TODO Auto-generated method stub
			try {
				TextMessageProducer producer=new TextMessageProducer();
				System.out.println("Now write somethings /n");
			      BufferedReader br = new BufferedReader
			                         (new InputStreamReader(System.in));
			      while(true) {
			    	  String msg = br.readLine();
			           if(msg.equalsIgnoreCase("exit")) {
			        	   producer.sendTextMessage(msg);
			        	   producer.close();
			                 System.exit(0);
			           } else {
			        	   producer.sendTextMessage(msg);
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
