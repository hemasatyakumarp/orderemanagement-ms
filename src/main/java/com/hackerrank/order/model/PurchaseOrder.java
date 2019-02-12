package com.hackerrank.order.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class PurchaseOrder {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Long customerId;
	
	private String paymentChannel;	

	private Boolean isCod;
	
	private String orderStatus;
	
	private Long orderCreatedOn;
	
	private Double totalAmount;
	
	private String shippingAddress;
	
	@OneToOne(cascade=CascadeType.ALL)
	private OrderLineItem orderLineItem;
	
	public Long getId() {
		return id;
	}
	public void setId(Long orderId) {
		this.id = orderId;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	public Boolean getIsCod() {
		return isCod;
	}
	public void setIsCod(Boolean isCod) {
		this.isCod = isCod;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Long getOrderCreatedOn() {
		return orderCreatedOn;
	}
	public void setOrderCreatedOn(Long orderCreatedOn) {
		this.orderCreatedOn = orderCreatedOn;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getShippingAddress() {
		return shippingAddress;
	}
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}
	public OrderLineItem getOrderLineItem() {
		return orderLineItem;
	}
	public void setOrderLineItem(OrderLineItem orderLineItem) {
		this.orderLineItem = orderLineItem;
	}
	
}
