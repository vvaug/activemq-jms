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

public class TopicConsumer2 {

	/*
	 * Author: Victor Silva
	 */
	
	public static void main(String[] args) throws NamingException, JMSException {
		
		InitialContext context = new InitialContext();
		Connection connection;
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		connection = factory.createConnection();
		connection.setClientID("conexaoEstoqueFilial");
		connection.start();
		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic = (Topic) context.lookup("loja");
		
		//MessageConsumer consumer = session.createConsumer(topic);
		MessageConsumer consumer = session.createDurableSubscriber(topic, "consumidorDeTopico2");
		
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
