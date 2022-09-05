package com.orderservice.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orderservice.entity.Order;
import com.orderservice.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	private static Logger log = LoggerFactory.getLogger(OrderService.class);
		
		
	public String createOrder(Order order) {
		
		try {
		order.setOrderStatus("PENDING");
		//adding the order in the database
		orderRepository.save(order);
		} catch(Exception ex) {
			log.error(ex.getMessage());
		}
		
			
		return "order created "+order.getOrderId();
	}

}
