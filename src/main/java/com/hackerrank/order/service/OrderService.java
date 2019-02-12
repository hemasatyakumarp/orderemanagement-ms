package com.hackerrank.order.service;

import com.hackerrank.order.rabbitmq.QueueProducer;
import com.hackerrank.order.exception.NoSuchResourceFoundException;
import com.hackerrank.order.model.PurchaseOrder;
import com.hackerrank.order.repository.OrderRepository;
import java.util.List;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

	public OrderService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private QueueProducer queueProducer;
    
	
	public List<PurchaseOrder> getOrdersByCustId(Long id) {
		List<PurchaseOrder> orderslist = orderRepository.findBySearch(id);
		if (orderslist.isEmpty()) {
			  throw new NoSuchResourceFoundException("No Customer with given id found.");
		}

		return orderslist;
	}

	public PurchaseOrder createOrder(PurchaseOrder order) throws Exception {
		PurchaseOrder existingOrder = orderRepository.findOne(order.getId());

		if (existingOrder != null) {
			return null;
		}

		orderRepository.save(order);
		queueProducer.produce(order.getOrderLineItem());			
		emailService.sendmail();
		return orderRepository.findOne(order.getId());
	}

	public PurchaseOrder getOrdersByOrderId(Long id) {
		PurchaseOrder order = orderRepository.findOne(id);

		if (order == null) {
			throw new NoSuchResourceFoundException("No Order with given id found.");
		}

		return order;
	}
	
	public PurchaseOrder updateOrder(Long orderId, PurchaseOrder order) {

		PurchaseOrder tempOrder = orderRepository.findOne(orderId);

		if (tempOrder == null) {
			return null;
		}

		orderRepository.save(order);
		return orderRepository.findOne(orderId);

	}

	public Boolean deleteOrderById(Long id) {
		PurchaseOrder order = orderRepository.findOne(id);

		if (order == null) {
			throw null;
		}

		orderRepository.deleteById(id);
		return true;
	}
	
	@Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("RabbitErl19");
        connectionFactory.setUsername("test123");
        connectionFactory.setPassword("test123");
        connectionFactory.setPort(15672);
        connectionFactory.setVirtualHost("localhost");
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }



}
