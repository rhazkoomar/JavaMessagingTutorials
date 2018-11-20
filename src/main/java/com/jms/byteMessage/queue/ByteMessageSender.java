package com.jms.byteMessage.queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.jms.objectMessage.topic.Student;

public class ByteMessageSender {

	private QueueConnectionFactory queueConnectionFactory;
	private QueueSession queueSession;
	private String queueName = "spring_jms_queue_destination";
	private String queueConnectionFactoryName = "queue_connection_factory";
	private Queue queue;
	private QueueConnection queueConnection;
	private QueueSender queueSender;

	public ByteMessageSender() throws NamingException, JMSException {
		InitialContext ctx = new InitialContext();
		// Step1: Lookup the Connection Factory and the Topic
		queueConnectionFactory = (QueueConnectionFactory) ctx.lookup(queueConnectionFactoryName);
		queue = (Queue) ctx.lookup(queueName);
		// Step2: Create a connection using the Factory
		queueConnection = queueConnectionFactory.createQueueConnection();
		// Step3: Create Topic Sessions using the connection
		queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		// Step4: Create TopicPublisher
		queueSender = queueSession.createSender(queue);
		queueConnection.start();
	}

	public void close() throws JMSException {
		queueConnection.stop();
	}

	public void sendBytesMessage(String contents) throws JMSException {
		// Creating a Text Message with the String object
		BytesMessage bytesMessage = queueSession.createBytesMessage();
		bytesMessage.writeBytes(contents.getBytes());
		queueSender.send(bytesMessage);
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		try {
			ByteMessageSender sender = new ByteMessageSender();
			System.out.println("Now write somethings /n");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			// List<Student> student=new ArrayList<Student>();

			// for(int i=0;i<10;i++) {
			// Student student=new Student();
			// student.setCollege(i+ " school");
			// student.setName(i+" name");
			// student.setFaculty(i+ " faculty");
			// sender.sendObjectMessage(student);
			// }
			sender.close();
			while (true) {
				String msg = br.readLine();
				if (msg.equalsIgnoreCase("exit")) {

					sender.sendBytesMessage(msg);
					sender.close();
					System.exit(0);
				} else {
					sender.sendBytesMessage(msg);
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
