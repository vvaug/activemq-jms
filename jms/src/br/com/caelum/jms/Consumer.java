package br.com.caelum.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Consumer {
	
	/*
	 * Author: Victor Silva
	 */
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws NamingException, JMSException {
	
		/*
		 * When we start the ActiveMQ instance, it automatically creates a register
		 * that is a JNDI in its container that we can get (look up) using the
		 * Initial Context (like the ConnectionFactorys, Queues, Topics, etc)
		 */
		
		InitialContext context = new InitialContext();

		ConnectionFactory cf = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection conexao = cf.createConnection();

		conexao.start();
		
		/*
		 * After getting the ConnectionFactory and established the connection,
		 * we can use this Connection object to create a session with the method 
		 * javax.jms.Connection.createSession(boolean arg1, int arg2)
		 * The first argument means that the session is not transacted; 
		 * the second means that the session automatically acknowledges messages 
		 * when they have been received successfully.
		 */
		
		Session session = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		/*
		 * To create a transacted session, use the following code:
         * Session session = connection.createSession(true, 0);
		 */
		
		/* Now, we have to get our Queue that we already created.
		 * The ActiveMQ also create in the JNDI references to our Queues
		 * that we can get using lookup and put it in a Destination object.
		 * But first, we have to configure our jndi.properties to create a reference
		 * to our Queue:
		 * queue.financeiro = fila.financeiro
		 * the "financeiro" references our queue fila.financeiro
		 */
		
		Destination fila = (Destination) context.lookup("financeiro");
		
		/* Now, have to create a Consumer to consume the message in the MOM.
		 * We have to create a MessageConsumer object that is created by
		 * method Session.createConsumer(Destionation ourQueue)
		 * that receives as parameter our Queue (Destination)
		 */
		
		MessageConsumer consumer = session.createConsumer(fila);
		
		/* The messages that we read, must be stored in a Message object.
		 * the MessageConsumer.receive() method returns a Message object
		 * so, we can read the message using the Message object returned by the
		 * MessageConsumer.receive() method
		 */
		
		Message message = consumer.receive();
		
		/*
		 * we can also use a int parameter as milliseconds to wait for a message.
		 * Example: consumer.receive(10000)
		 */
		
		System.out.println("New message received: " + message);
		
		new Scanner(System.in).nextLine();
		
		session.close();
		conexao.close();    
		context.close();
	}
}
