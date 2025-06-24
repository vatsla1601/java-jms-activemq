package org.example.jmsDemo.util;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class JMSUtil {

    //public static final String brokerUrl ="vm://localhost";
    public static final String brokerUrl ="tcp://localhost:61616";
    public static final String queueName ="vatsla.queue";

    public static Connection createConnection() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
        return connectionFactory.createConnection();
    }

    public static Session createSession(Connection connection) throws JMSException {
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public static Destination getQueue(Session session) throws JMSException {
        return session.createQueue(queueName);
    }

}
