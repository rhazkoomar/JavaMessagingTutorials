package com.jms.objectMessage.topic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jms.textMessage.queue.QueueTextMessageSender;


public class TopicObjectMessageSender {
	
	 private TopicConnectionFactory topicConnectionFactory;
	 private TopicSession topicSession;
	 private String topicName = "spring_jms_topic_destination";
	 private String topicConnectionFactoryName ="topic_connection_factory";
	 private Topic topic;
	 private TopicConnection topicConnection;
	 private TopicPublisher topicPublisher;
	 
	 public TopicObjectMessageSender() 
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

	 public void sendObjectMessage(Student student) throws JMSException {
	       //  Creating a Text Message with the String object
	       ObjectMessage objectMessage = topicSession.createObjectMessage();
	       objectMessage.setObject(student);

	       //  Publishing the message object to the Topic
	       topicPublisher.send(objectMessage);
	 }
	 
	 public static void main(String[] args) throws IOException {
			// TODO Auto-generated method stub
			
			try {
				TopicObjectMessageSender sender=new TopicObjectMessageSender();
				System.out.println("Now write somethings /n");
			      BufferedReader br = new BufferedReader
			                         (new InputStreamReader(System.in));
//			      for(int i=0;i<10;i++) {
//			    	  Student student=new Student();
//			    	  student.setCollege(i+ " school");
//			    	  student.setName(i+" name");
//			    	  student.setFaculty(i+ " faculty");
//			    	  sender.sendObjectMessage(student);
//			      }
			      sender.close();
			      while(true) {
			    	  String msg = br.readLine();
			    	  Student student=new Student();
			    	  student.setCollege(msg+ " school");
			    	  student.setName(msg+" name");
			    	  student.setFaculty(msg+ " faculty");
			           if(msg.equalsIgnoreCase("exit")) {
			        	   
			        	   sender.sendObjectMessage(student);
			        	   sender.close();
			                 System.exit(0);
			           } else {
			        	   sender.sendObjectMessage(student);
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
