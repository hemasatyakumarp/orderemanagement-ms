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
import org.springframework.messaging.MessagingException;
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
	public ResponseEntity<PurchaseOrder> createOrder(@RequestBody PurchaseOrder order) throws Exception {
      /* PurchaseOrder po = service.createOrder(order);
       if (po==null) {
    	   return new ResponseEntity("Order Id already exists", HttpStatus.FOUND);
       }  else {
    	 
        return new ResponseEntity(po, HttpStatus.CREATED);
       }*/
		int i = service.createOrder(order);
		if (i==0 ) {
			return new ResponseEntity("Order Id already exists", HttpStatus.FOUND);
		} else {
			if(i==-1) {
			return new ResponseEntity("Product Not Available at Customer's Address", HttpStatus.NOT_ACCEPTABLE);
		} else {
			return new ResponseEntity(service.getOrdersByOrderId(order.getId()), HttpStatus.CREATED);
		}
		}
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
