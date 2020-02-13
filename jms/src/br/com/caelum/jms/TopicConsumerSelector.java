package br.com.caelum.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TopicConsumerSelector {

	public static void main(String[] args) throws NamingException, JMSException {
		InitialContext context = new InitialContext();
		Connection connection;
		ConnectionFactory factory;
		
		factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		connection = factory.createConnection();
		connection.setClientID("estoque-selector");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Topic topic = (Topic) context.lookup("loja");
		
		MessageConsumer consumer = session.
				                   createDurableSubscriber(topic, 
				                		                   "assinatura_selector", 
				                		                   "ebook= false", 
				                		                   false);
		
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message arg0) {
				TextMessage text = (TextMessage) arg0;
				try {
					System.out.println(text.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}
}
