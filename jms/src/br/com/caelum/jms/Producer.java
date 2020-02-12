package br.com.caelum.jms;

import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Producer {
	
	/*
	 * Author: Victor Silva
	 */

	public static void main(String[] args) throws JMSException, NamingException {
		
		InitialContext context = new InitialContext();
		
		Connection connection;
		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination queue = (Destination) context.lookup("financeiro");
		
		MessageProducer messageProducer = session.createProducer(queue);
	    
        Message message = session.createTextMessage("Message Send: " + new Date());
        
        System.out.println("Sending message...");
        messageProducer.send(message);
        System.out.println("Message send...");
        
        session.close();
        connection.close();
	}
}
