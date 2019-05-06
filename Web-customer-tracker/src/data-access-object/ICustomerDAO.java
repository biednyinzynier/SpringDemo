package com.burak.springdemo.dao;

import java.util.List;

import com.burak.springdemo.entity.Customer;

public interface ICustomerDAO {
	
	public List<Customer> getCustomers();

	public void saveCustomer(Customer customer);

	public Customer getCustomer(int id);

	public void deleteCustomer(int id);

	public List<Customer> searchCustomers(String theSearchName);
}
