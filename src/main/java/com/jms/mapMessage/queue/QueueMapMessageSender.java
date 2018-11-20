package com.jms.mapMessage.queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jms.objectMessage.topic.Student;
import com.jms.textMessage.queue.QueueTextMessageSender;


public class QueueMapMessageSender {
	
	 private QueueConnectionFactory queueConnectionFactory;
	 private QueueSession queueSession;
	 private String queueName = "spring_jms_queue_destination";
	 private String queueConnectionFactoryName = "queue_connection_factory";
	 private Queue queue;
	 private QueueConnection queueConnection;
	 private QueueSender queueSender;
	 
	 public QueueMapMessageSender() 
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

	 public void sendMapMessage(Student student) throws JMSException {
	       //  Creating a Text Message with the String object
		 MapMessage MapMessage = queueSession.createMapMessage();
		 MapMessage.setString("Name", student.getName());
		 MapMessage.setString("StudentId", student.getStudentId());
		 MapMessage.setString("Faculty", student.getFaculty());
		 MapMessage.setString("College", student.getCollege());
	       //  Publishing the message object to the Topic
	       queueSender.send(MapMessage);
	 }
	 
	 public static void main(String[] args) throws IOException {
			// TODO Auto-generated method stub
			
			try {
				QueueMapMessageSender sender=new QueueMapMessageSender();
				System.out.println("Now write somethings /n");
			      BufferedReader br = new BufferedReader
			                         (new InputStreamReader(System.in));
//			      List<Student> student=new ArrayList<Student>();
			      
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
			    	  student.setStudentId(msg+ " Id");
			    	  student.setFaculty(msg+ " faculty");
			           if(msg.equalsIgnoreCase("exit")) {
			        	   
			        	   sender.sendMapMessage(student);
			        	   sender.close();
			                 System.exit(0);
			           } else {
			        	   sender.sendMapMessage(student);
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
