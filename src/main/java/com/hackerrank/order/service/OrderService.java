package com.hackerrank.order.service;

import com.hackerrank.order.exception.NoSuchResourceFoundException;
import com.hackerrank.order.model.PurchaseOrder;
import com.hackerrank.order.repository.OrderRepository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

	public OrderService() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private OrderRepository orderRepository;

	public List<PurchaseOrder> getOrdersByCustId(Long id) {
		List<PurchaseOrder> orderslist = orderRepository.findBySearch(id);
		if (orderslist.isEmpty()) {
			  throw new NoSuchResourceFoundException("No Customer with given id found.");
		}

		return orderslist;
	}

	public PurchaseOrder createOrder(PurchaseOrder order) {
		PurchaseOrder existingOrder = orderRepository.findOne(order.getId());

		if (existingOrder != null) {
			return null;
		}

		orderRepository.save(order);
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

}
