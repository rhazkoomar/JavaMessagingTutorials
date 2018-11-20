package com.jms.byteMessage.queue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ByteMessageReceiver implements MessageListener {
	private QueueConnectionFactory queueConnectionFactory;
	private QueueSession queueSession;
	private String queueName = "spring_jms_queue_destination";
	private String queueConnectionFactoryName = "queue_connection_factory";
	private Queue queue;
	private QueueConnection queueConnection;
	private QueueReceiver queueReceiver;

	public ByteMessageReceiver() throws NamingException, JMSException {

		InitialContext ctx = new InitialContext();

		// Step1: Lookup the Connection Factory and the Topic
		queueConnectionFactory = (QueueConnectionFactory) ctx.lookup(queueConnectionFactoryName);
		queue = (Queue) ctx.lookup(queueName);

		// Step2: Create a connection using the Factory
		queueConnection = queueConnectionFactory.createQueueConnection();

		// Step3: Create Topic Sessions using the connection
		queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

		// Step4: Create TopicPublisher
		queueReceiver = queueSession.createReceiver(queue);
		queueReceiver.setMessageListener(this);

		queueConnection.start();
	}

	public void close() throws JMSException {
		queueConnection.stop();
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		if (message instanceof BytesMessage) {
			try {
				BytesMessage bytesMessage = (BytesMessage) message;
				System.out.println("message==>"+message);
				int TEXT_LENGTH=new Long(bytesMessage.getBodyLength()).intValue();
				byte[] textBytes= new byte[TEXT_LENGTH];
				bytesMessage.readBytes(textBytes, TEXT_LENGTH);
				String contents=new String(textBytes);
				System.out.println("contents==>"+contents);
				System.out.println("===========================================");

			
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) throws NamingException, JMSException {
		ByteMessageReceiver rec = new ByteMessageReceiver();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			if (br.readLine().equalsIgnoreCase("exit")) {
				br.close();
				rec.close();
			}
		} catch (IOException ie) {
			System.out.println("IOExceptionrred");
			System.exit(0);
		}
	}

}
