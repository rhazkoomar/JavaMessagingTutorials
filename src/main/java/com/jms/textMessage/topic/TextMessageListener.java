package com.jms.textMessage.topic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TextMessageListener implements MessageListener {

	 private TopicConnectionFactory topicConnectionFactory;
	 private TopicSession topicSession;
	 private String topicName = "jms/MyTopic";
	 private String topicConnectionFactoryName = "jms/MyConnectionFactory";
	 private Topic topic;
	 private TopicConnection topicConnection;
	 private TopicSubscriber topicSubscriber;
	 
	 public TextMessageListener() 
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
		       topicSubscriber = topicSession.createSubscriber(topic);
		       topicSubscriber.setMessageListener(this);
		       topicConnection.start();
		}
	 
	 public void close() throws JMSException{
		 topicConnection.stop();
	 }

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		if(message instanceof TextMessage) {
			TextMessage textMessage=(TextMessage)message;
			System.out.println("textMessage==>"+textMessage);
		}
		
	}
	
	  public static void main(String[] args) 
	          throws NamingException,JMSException {

	       try {
	            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//	            if(args.length == 2) {
//	                uname = args[0];
//	                pwd = args[1];
//	            } else {
//	                System.out.println("Notgh parameters");
//	                System.exit(0);
//	            }

	           //  Just create the Subscriber object
	            TextMessageListener sub = new TextMessageListener();

	           //  Exiting the Subscriber
	            if(br.readLine().equalsIgnoreCase("exit")) {
	                br.close();
	                sub.close();
	            }
	       } catch(IOException ie) {
	           System.out.println("IOExceptionrred");
	           System.exit(0);
	       }
	    }

}
