package com.hackerrank.order.controller;

import java.io.IOException;
import java.util.List;

import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.hackerrank.order.service.OrderService;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import com.hackerrank.order.model.*;

@RestController
public class OrderController {

	@Autowired
	private OrderService service;

	@RequestMapping(value = "/order/customer/{custId}", method = RequestMethod.GET)
	public ResponseEntity<List<PurchaseOrder>> getOrdersByCustId(@PathVariable("custId") Long id) {
		List<PurchaseOrder> orderslist = service.getOrdersByCustId(id);		
		return new ResponseEntity(orderslist, HttpStatus.OK);

	}

	@RequestMapping(value = "/order/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<PurchaseOrder> getOrdersByOrderId(@PathVariable("orderId") Long id) {
		PurchaseOrder c = service.getOrdersByOrderId(id);
		if (c == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(c, HttpStatus.OK);

	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public ResponseEntity<PurchaseOrder> createOrder(@RequestBody PurchaseOrder order) throws AddressException, MessagingException, IOException {

		return new ResponseEntity(service.createOrder(order), HttpStatus.CREATED);
	}
	
	

	@RequestMapping(value = "/order/{id}", method = {RequestMethod.PATCH,RequestMethod.PUT})
	public ResponseEntity<PurchaseOrder> updateOrder(@PathVariable("id") Long orderId, @RequestBody PurchaseOrder order) {

		PurchaseOrder c = service.updateOrder(orderId, order);
		if (c == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(c, HttpStatus.OK);

	}

	@RequestMapping(value = "/order/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteOrder(@PathVariable("id") Long id) {
		Boolean b = service.deleteOrderById(id);
		if (b) {
			return new ResponseEntity("Order Deleted", HttpStatus.OK);
		}

		return new ResponseEntity("Order Not Found", HttpStatus.NOT_FOUND);
	}

}
