package org.tsys.sbb.util;

import org.tsys.sbb.controller.ScheduleController;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.Hashtable;

@Startup
@Singleton
public class MyListener implements MessageListener, Serializable {

    @EJB
    private ScheduleController scheduleController;

    @PostConstruct
    public void receive() {

        QueueConnection connection;
        QueueSession session;
        QueueReceiver receiver;

        Hashtable<String, String> props = new Hashtable<>();
        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put("java.naming.provider.url", "tcp://localhost:61616");
        props.put("queue.js-queue", "sbb-schedule");
        props.put("connectionFactoryNames", "queueCF");

        try {
            Context context = new InitialContext(props);
            QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup("queueCF");
            Queue queue = (Queue) context.lookup("js-queue");

            connection = connectionFactory.createQueueConnection();
            connection.start();

            session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);

            receiver = session.createReceiver(queue);

            receiver.setMessageListener(new MyListener());

        } catch (NamingException | JMSException e) {
            System.out.println("Error during message recieving");
        }
    }

    public void onMessage(Message message) {
        scheduleController.recieveSchedule();
        System.out.println("Got a message!");
    }
}
