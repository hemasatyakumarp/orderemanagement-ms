package com.hackerrank.order.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
public class Customer implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    private Long id;
    private String customerName;
    private Long contactNumber;
    private String address;
    private String gender;

    
    public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer(Long id, String name, Long number, String address, String gender) {
        this.id = id;
        this.customerName = name;
        this.contactNumber = number;
        this.address = address;
        this.gender = gender;
    }
    public Long getId() {
		return id;
	}

	public void setId(Long customerId) {
		this.id = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(Long contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

    
}
