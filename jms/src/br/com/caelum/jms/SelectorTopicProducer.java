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

public class SelectorTopicProducer {

	/*
	 * Author: Victor Silva
	 */
	
	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext context = new InitialContext();
		Connection connection;
		ConnectionFactory factory;
		
		factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination topic = (Destination) context.lookup("loja");
		
		MessageProducer producer = session.createProducer(topic);
		
		Message text = session.createTextMessage("Topic message send: " + new Date());
		
		text.setBooleanProperty("ebook", false);
		
		producer.send(text);
		
	}
}
