package com.example.demo.jms;

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

public class Subscriber implements MessageListener {

private TopicConnectionFactory tCF;
private TopicConnection tCon;
private TopicSession subSession;
private String topicName = "jms/MyTopic";
private Topic top;
private TopicSubscriber tSub;

// Constructor to initialise the required entities

public Subscriber(String uname, String pwd) 
       throws NamingException,JMSException {

    InitialContext ctx = new InitialContext();

    // Step1: Lookup the Connection Factory and the Topic
    tCF = (TopicConnectionFactory)
          ctx.lookup("jms/MyConnectionFactory");
    top = (Topic)ctx.lookup(topicName);

    // Step2: Create a connection using the Factory
    tCon = tCF.createTopicConnection();

    // Step3: Create Topic Sessions using the connection
    subSession = tCon.createTopicSession
                (false,Session.AUTO_ACKNOWLEDGE);

    // Step4: Create TopicPublisher
    tSub = subSession.createSubscriber(top);

    //  Associating a MessageListener to this subscriber
    tSub.setMessageListener(this);

    tCon.start();
}
   // Message Parsing logic from the Topic
   public void onMessage(Message msg) {
      try {
          //  Extracting the TextMessage object from the Message
          TextMessage txtMsg = (TextMessage)msg;

          //  From the TextMessage object, extract the message
          String text = txtMsg.getText();

          /*  
           *  If message is not 'exit', print the Notice from
           *  Publisher. Otherwise, close the connection and exit
           */
          if(!text.equalsIgnoreCase("exit")) {
                System.out.println("Notice text==>"+text);
          } else {
                System.out.println("Good");
                exit();
          }
      } catch(JMSException je) {
            je.printStackTrace();
      }
   }

   //   Exit module where in connection object is closed
   public void exit() {
      try {
          tCon.close();
      } catch(JMSException je) {
          System.out.println("JMSExceptionrred");
      }
      System.exit(0);
   }

   public static void main(String[] args) 
          throws NamingException,JMSException {

       try {
            String uname = "Subscriber", pwd = "PASSWORD";
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//            if(args.length == 2) {
//                uname = args[0];
//                pwd = args[1];
//            } else {
//                System.out.println("Notgh parameters");
//                System.exit(0);
//            }

           //  Just create the Subscriber object
            Subscriber sub = new Subscriber(uname,pwd);

           //  Exiting the Subscriber
            if(br.readLine().equalsIgnoreCase("exit")) {
                br.close();
                sub.exit();
            }
       } catch(IOException ie) {
           System.out.println("IOExceptionrred");
           System.exit(0);
       }
    }
}
