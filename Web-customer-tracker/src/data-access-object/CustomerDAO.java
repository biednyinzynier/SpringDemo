package com.burak.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.burak.springdemo.entity.Customer;

// Repository annotation; we need for Spring can component scan and find this repo
// Also handle the exception translation
@Repository
public class CustomerDAO implements ICustomerDAO {

	// need to inject the session factory
	// so DAO can use it to talking the database
	@Autowired
	private SessionFactory sessionFactory;
	
	
	
	@Override
	public List<Customer> getCustomers() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// create query and sort by the first name
		Query<Customer> query = 
				currentSession.createQuery("from Customer order by firstName", 
											Customer.class);
		
		// execute query and get result list
		List<Customer> customers = query.getResultList();
		
		// return the results
		return customers;
	}



	@Override
	public void saveCustomer(Customer customer) {
		
		// get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// save/update the customer
		currentSession.saveOrUpdate(customer);
	}



	@Override
	public Customer getCustomer(int id) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// retrieve data from database using the primary key
		Customer customer = currentSession.get(Customer.class, id);
		
		return customer;
	}


	@Override
	public void deleteCustomer(int id) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// delete object with primary key
		Query query = 
				currentSession.createQuery("delete from Customer where id=:customerId");
		query.setParameter("customerId", id);
		
		query.executeUpdate();
		
	}



	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		Query query = null;
		
		// search only by name if thesearchname is not empty
		if(theSearchName != null && theSearchName.trim().length()>0) {
			
			/* search for firstname or lastname
			For the condition when "theSearchName" is not empty, 
			then we use it to compare against the first name or last name.
			We also make use of the "like" clause and the "%" wildcard characters. 
			This will allow us to search for substrings*/
 
			query = currentSession.createQuery("from Customer where "
					+ "lower(firstName) like :theName or lower(lastName) like:theName", Customer.class);
			
			query.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
		}
		else {
			
			// the search name is empty get all customers
			query = currentSession.createQuery("from Customer", Customer.class);
			
		}
		
		// execute query and get result list
		List<Customer> customers = query.getResultList();
		
		return customers;
	}

}
