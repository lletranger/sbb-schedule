package org.tsys.sbb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tsys.sbb.controller.ScheduleController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Hashtable;

@Startup
@Singleton
public class MyListener implements MessageListener {

    QueueConnection connection;
    QueueSession session;
    QueueReceiver receiver;

    @EJB
    private ScheduleController scheduleController;

    private static final Logger logger = LoggerFactory.getLogger(MyListener.class);


    @PostConstruct
    public void receive() {

        Hashtable<String, String> props = new Hashtable<>();
        props.put("java.naming.factory.initial", "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.put("java.naming.provider.url", "tcp://localhost:61616");
        props.put("queue.js-queue", "sbb-schedule");
        props.put("connectionFactoryNames", "queueCF");

        try {
            Context context = new InitialContext(props);
            QueueConnectionFactory connectionFactory = (QueueConnectionFactory) context.lookup("queueCF");
            Queue queue = (Queue) context.lookup("js-queue");
            QueueConnection connection = connectionFactory.createQueueConnection();
            connection.start();
            session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
            receiver = session.createReceiver(queue);
            receiver.setMessageListener(this);

        } catch (Exception e) {
            logger.info("Error during message receiving");
        }
    }

    @Override
    public void onMessage(Message message) {
        logger.info("Got a new message! Getting new schedule");
        try {
            scheduleController.receiveSchedule();
            logger.info("Schedule renewed");
        } catch (Exception e) {
            logger.info("Problem with the ScheduleController!");
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            receiver.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            logger.info("Error during listener destroying");
        }
    }
}