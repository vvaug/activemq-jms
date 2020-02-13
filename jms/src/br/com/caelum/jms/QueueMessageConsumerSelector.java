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

public class QueueMessageConsumerSelector {

	public static void main(String[] args) throws NamingException, JMSException {
		Connection connection;
		ConnectionFactory factory;
		InitialContext context = new InitialContext();
		
		factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		connection = factory.createConnection();
		connection.start();
		
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		
		Destination queue = (Destination) context.lookup("financeiro");
		
		MessageConsumer messageConsumer = session.createConsumer(queue, "compra=true OR venda=true");
		
		messageConsumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message arg0) {
				TextMessage text = (TextMessage) arg0;
				try {
					System.out.println("New message: " + text.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
}
