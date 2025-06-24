package org.example.jmsDemo.producer;

import org.example.jmsDemo.util.JMSUtil;
import javax.jms.*;
import java.util.logging.Logger;

public class HelloWorldProducer{

    public static void main(String[] args) throws JMSException {
        JMSUtil jmsUtil = new JMSUtil();
        Logger logger = Logger.getLogger("HelloWorldProducer");

        Connection connection = null;
        Session session = null;

        try {
            connection = jmsUtil.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            logger.severe(e.getMessage());
            e.getStackTrace();
        }
        Destination queue = session.createQueue("vatsla.queue");
        MessageProducer producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        int x=0;
        while (x<5){
            String text = "Message " + x;
            TextMessage message = session.createTextMessage(text);
            producer.send(message);
            logger.info("Sent " + text);
            x++;
        }
        producer.close();
        session.close();
        connection.close();
    }
}
