package org.example.jmsDemo.consumer;

import org.apache.camel.Consumer;
import org.example.jmsDemo.util.JMSUtil;

import javax.jms.*;
import java.util.logging.Logger;

public class HelloWorldConsumer implements Runnable, MessageListener {

    Logger logger = Logger.getLogger("HelloWorldConsumer");

    @Override
    public void run() {
        JMSUtil jmsUtil = new JMSUtil();

        try {
            Connection connection = jmsUtil.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination queue = session.createQueue("vatsla.queue");
            MessageConsumer consumer = session.createConsumer(queue);
            consumer.setMessageListener(this);
            logger.info("Recieved.");
            Thread.sleep(1000000);

            consumer.close();
            session.close();
            connection.close();
        } catch (InterruptedException e) {
            logger.severe(e.getMessage());
            e.printStackTrace();
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String text = ((TextMessage) message).getText();
                logger.info("Consumer text: " + text);
            } else {
                logger.warning("Recieved non-text message");
            }
        } catch (Exception e) {
            logger.severe("Something went wrong: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Thread(new HelloWorldConsumer()).start();
    }

}
