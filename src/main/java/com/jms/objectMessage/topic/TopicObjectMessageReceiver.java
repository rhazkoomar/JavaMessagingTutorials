package com.jms.objectMessage.topic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TopicObjectMessageReceiver implements MessageListener {
	 private TopicConnectionFactory topicConnectionFactory;
	 private TopicSession topicSession;
	 private String topicName = "spring_jms_topic_destination";
	 private String topicConnectionFactoryName ="topic_connection_factory";
	 private Topic topic;
	 private TopicConnection topicConnection;
	 private TopicSubscriber topicSubscriber;
	 
	 public TopicObjectMessageReceiver() 
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
		if(message instanceof ObjectMessage) {
			ObjectMessage objectMessage=(ObjectMessage)message;
			
			try {
				Student student=objectMessage.getBody(Student.class);

				System.out.println("Student==>"+student);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	  public static void main(String[] args) 
	          throws NamingException,JMSException {
		  TopicObjectMessageReceiver rec=new TopicObjectMessageReceiver();
	       try {
	            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	            if(br.readLine().equalsIgnoreCase("exit")) {
	                br.close();
	                rec.close();
	            }
	       } catch(IOException ie) {
	           System.out.println("IOExceptionrred");
	           System.exit(0);
	       }
	    }


}
