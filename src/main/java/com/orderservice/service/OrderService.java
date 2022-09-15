package com.orderservice.service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.orderservice.entity.Order;
import com.orderservice.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	RestTemplate  restTemplate;
	
	@Autowired
	private OrderRepository orderRepository;
	private static Logger log = LoggerFactory.getLogger(OrderService.class);
	
	//spring boot creates jmstemplate bean automatically
	//using autoconfiguration
	@Autowired
	JmsTemplate jmsTemplate;
	
	
	
	public String createOrder(Order order) {
		
		order.setOrderStatus("PENDING");
		//adding the order in the database
		orderRepository.save(order);
		
		//logic for producing message to order event queue
		//jmsTemplate.convertAndSend(ORDER_EVENT_QUEUE, order);
		
		return "order created "+order.getOrderId();
	}

	public java.util.List<Order> getOrders() {
		return orderRepository.findAll();
	}

	
}
