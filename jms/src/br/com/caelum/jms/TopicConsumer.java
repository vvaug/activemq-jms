package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TopicConsumer {

	/*
	 * Author: Victor Silva
	 */
	
	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext context = new InitialContext();
		Connection connection;
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination queue = (Destination) context.lookup("loja");
		MessageConsumer consumer = session.createConsumer(queue);
		
		/*
		 * Set a message listener (anonymous MessageListener interface)
		 * to the consumer object because we want to listen to messages all the time.
		 * and implement the interface method onMessage 
		 */
		
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message arg0) {
				TextMessage textMessage = (TextMessage) arg0;
				try {
					System.out.println(textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		//connection.close();
		//context.close();
	}
}
