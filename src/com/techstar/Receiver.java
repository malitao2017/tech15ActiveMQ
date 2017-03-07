/**   
 * Copyright © 2015 北京恒泰实达科技发展有限公司. All rights reserved.
 * 项目名称：tech15ActiveMQ
 * 描述信息: 
 * 创建日期：2015年12月30日 下午4:49:03 
 * @author malitao
 * @version 
 */
package com.techstar;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver {
    public static void main(String[] args) {
        //JMS 客户端到JMS Provider 的连接,最后需要关闭
        Connection connection = null;
        try {
        	//连接工厂，JMS 用它创建连接
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD,
            		"tcp://localhost:61616");

            connection = connectionFactory.createConnection(); // 构造从工厂得到连接对象
            connection.start();// 启动
            //一个发送或接收消息的线程
            Session session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);// 获取操作连接
            
            // 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置 //消息的目的地;消息发送给谁.
            Destination destination = session.createQueue("FirstQueue");
            
            //消费者，消息接收者
            MessageConsumer consumer = session.createConsumer(destination);
            
            while (true) {
                //设置接收者接收消息的时间，为了便于测试，这里谁定为100s
                TextMessage message = (TextMessage) consumer.receive(500*1000);
                if (null != message) {
                    System.out.println("接受者收到消息" + message.getText());
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection)
                    connection.close();
            } catch (Throwable ignore) {
            }
        }
    }
}