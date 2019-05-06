package com.burak.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.burak.springdemo.entity.Customer;
import com.burak.springdemo.service.ICustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	// need to inject CustomerService into this controller
	// Using spring's DI with using autowired
	@Autowired
	private ICustomerService customerService;
	
	// only respond to get  
	@GetMapping("/list")
	public String listCustomers(Model model) {
		
		// get customers from the service
		List<Customer> theCustomers = customerService.getCustomers();
		
		// add those customers to the model
		// name=customers		value=theCustomers
		model.addAttribute("customers", theCustomers);
		
		return "list-customers";
	}
	
	
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model) {
		
		// create model attribute to bind form data
		Customer customer = new Customer();
		
		model.addAttribute("customer", customer);
		
		return "customer-form";
	}
	
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer) {
		
		// save the customer using our service
		customerService.saveCustomer(customer);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id, Model model) {
		
		// get the customer form the database
		Customer customer = customerService.getCustomer(id);
		
		// set customer as a model attribute to pre-populate the form
		model.addAttribute("customer", customer);
		
		// send over the form
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id) {
		
		// delete the customer
		customerService.deleteCustomer(id);
		
		return "redirect:/customer/list";
	}
	
	@GetMapping("/search")
	public String searchCustomers(@RequestParam("theSearchName") 
								String theSearchName, Model model) {
		
		// search customers from the service
		List<Customer> customers = customerService.searchCustomers(theSearchName);
		
		// add the customers to the model
		model.addAttribute("customers", customers);
		
		return "list-customers";
	}
	
}
