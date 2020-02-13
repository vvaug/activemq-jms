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

public class QueueMessageProducerSelector {

	public static void main(String[] args) throws NamingException, JMSException {
		Connection connection;
		ConnectionFactory factory;
		InitialContext context = new InitialContext();
		
		factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination queue = (Destination) context.lookup("financeiro");
		
		Message message = session.createTextMessage("Hey, new message2!");
		message.setBooleanProperty("compra", true);
		message.setBooleanProperty("venda", false);
		
		MessageProducer messageProducer = session.createProducer(queue);
		messageProducer.send(message);
		
		session.close();
		connection.close();
	}
}
