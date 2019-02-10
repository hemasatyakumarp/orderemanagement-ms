package com.hackerrank.order.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class OrderLineItem implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long orderLineItemId;	
	
	private Long skuId;
	
	private Long orderId;
	
	private int itemQty;
	public Long getOrderLineItemId() {
		return orderLineItemId;
	}
	public void setOrderLineItemId(Long orderLineItemId) {
		this.orderLineItemId = orderLineItemId;
	}
	public Long getSkuId() {
		return skuId;
	}
	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public int getItemQty() {
		return itemQty;
	}
	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}


}
