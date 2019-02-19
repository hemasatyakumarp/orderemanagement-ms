package com.hackerrank.order.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.order.model.OrderLineItem;

@Component
public class QueueProducer {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Value("${rabbitmq.exchange}")
	private String exchange;
	@Value("${rabbitmq.routingkey}")
	private String routingKey;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void produce(OrderLineItem orderLineItem) throws Exception {
		logger.info("Storing notification...");
		//rabbitTemplate.convertAndSend(exchange,routingKey,new ObjectMapper().writeValueAsString(orderLineItem));
		rabbitTemplate.convertAndSend(exchange,routingKey,orderLineItem);
		logger.info("Notification stored in queue sucessfully");
	}
}