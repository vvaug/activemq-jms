package br.com.caelum.jms;

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
	    
        Message message = session.createTextMessage("I'm sending a new Message!");
        
        messageProducer.send(message);
        
        session.close();
        connection.close();
	}
}
