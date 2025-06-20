package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class App {

    public static void main(String[] args) throws Exception {
        thread(new HelloWorldProducer(), false);
        thread(new HelloWorldConsumer(), false);
    }

    public static void thread(Runnable runnable, boolean daemon) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(daemon);
        thread.start();
    }

    public static class HelloWorldProducer implements Runnable {
        public void run() {
            try {
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
                Connection connection = connectionFactory.createConnection();
                connection.start();

                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createQueue("vatsla.Destination");

                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                int x = 0;

                while(x<5) {
                    String text = x + Thread.currentThread().getName();
                    TextMessage message = session.createTextMessage(text);

                    System.out.println("Sent: " + text);
                    producer.send(message);
                    x++;
                }

                session.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class HelloWorldConsumer implements Runnable, ExceptionListener {
        public void run() {
            try {
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
                Connection connection = connectionFactory.createConnection();
                connection.start();

                connection.setExceptionListener(this);
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createQueue("vatsla.Destination");

                MessageConsumer consumer = session.createConsumer(destination);
                //Message message = consumer.receive(1000); // waits up to 1000ms for ONE message

                Message message;
                while ((message = consumer.receive(1000)) != null) {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println("Received: " + textMessage.getText());
                    } else {
                        System.out.println("Received non-text message");
                    }
                }

                consumer.close();
                session.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public synchronized void onException(JMSException ex) {
            System.out.println("JMS Exception occurred. Shutting down client.");
        }
    }
}
