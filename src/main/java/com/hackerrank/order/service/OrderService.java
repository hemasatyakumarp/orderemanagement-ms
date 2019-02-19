package com.hackerrank.order.service;

import com.hackerrank.order.rabbitmq.QueueProducer;
import com.hackerrank.order.exception.NoSuchResourceFoundException;
import com.hackerrank.order.model.Customer;
import com.hackerrank.order.model.PurchaseOrder;
import com.hackerrank.order.repository.OrderRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


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
		
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${security.user.password}")
	private String userName;
	
	@Value("${security.user.name}")
	private String userPassword;
	
	private ArrayList<String> addressList = new ArrayList<String>( Arrays.asList("address1", "address2", "address3"));

	public List<PurchaseOrder> getOrdersByCustId(Long id) {
		List<PurchaseOrder> orderslist = orderRepository.findBySearch(id);
		if (orderslist.isEmpty()) {
			  throw new NoSuchResourceFoundException("No Customer with given id found.");
		}

		return orderslist;
	}

	public int createOrder(PurchaseOrder order) throws Exception {
	  
		if (orderRepository.existsById(order.getId())) {
			return 0;
		} else {	
					}
		if (checkAvailability(order)) {					
		orderRepository.save(order);		
		getDiscountCoupon(order);	
		//queueProducer.produce(order.getOrderLineItem());			
		//emailService.sendmail();
		return 1;
		} else {
			return -1;
		}
		
	}

	public PurchaseOrder getOrdersByOrderId(Long id) {
		 if(!orderRepository.existsById(id)){

			throw new NoSuchResourceFoundException("No Order with given id found.");
		}

		return orderRepository.findById(id).get();
	}
	
	public PurchaseOrder updateOrder(Long orderId, PurchaseOrder order) {


		if (!orderRepository.existsById(orderId)) {
			return null;
		}

		orderRepository.save(order);
		return orderRepository.findById(orderId).get();

	}

	public Boolean deleteOrderById(Long id) {
		
		if (!orderRepository.existsById(id)) {
			return false;
		} else {
		orderRepository.deleteById(id);
		return true;
		}
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@HystrixCommand(commandKey="Discount",fallbackMethod = "getDiscountCouponFallback",  commandProperties = {
		      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")})
	public void getDiscountCoupon(PurchaseOrder order) {	
		
	  Double amount = order.getTotalAmount();
   	  System.out.println("***********Calling Discount Service************");
   	  String response = restTemplate.exchange("http://localhost:9999/api/discount-service/discount/{amount}",HttpMethod.GET, null, String.class,amount).getBody();
   	  System.out.println("***********Discount Applied is "+response+"%");
	}
	
	public void getDiscountCouponFallback(PurchaseOrder order) {
		
    System.out.println("************In the Fallback Method***Discount Verification Not Done************");
		
	}
	
	public Boolean checkAvailability(PurchaseOrder order) {
		
		  HttpHeaders headers = new HttpHeaders();
		  headers.setBasicAuth(userName, userPassword);
		  HttpEntity<String> entity = new HttpEntity<String>(headers);
	   	  System.out.println("***********Checking Availability in Customer's Location "+ order.getShippingAddress());
	   	  String url = "http://localhost:9999/api/customer-service/customer/"+order.getCustomerId();
	   	  Customer response = restTemplate.exchange(url,HttpMethod.GET,entity,Customer.class).getBody();
	   	  String address = response.getAddress();	
	   	  if (addressList.contains(address)){	   		   
	       System.out.println("***********Product is Available in Customer's Location "+address);
		    return true;
	   	  } else {	
	   	   System.out.println("***********Product is not Available in Customer's Location "+address);
	   		  return false;
	   	  }
		
	}

}
