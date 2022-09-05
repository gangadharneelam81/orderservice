package com.orderservice.service;

import static com.orderservice.config.ActiveMQConfig.FAILED_EVENT_QUEUE;
import static com.orderservice.config.ActiveMQConfig.ORDER_EVENT_QUEUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
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
	
	@JmsListener(destination=FAILED_EVENT_QUEUE)
	public void receiveOrderData(@Payload Order order) {
		try {
			Thread.sleep(5000);
		} catch(Exception ex) {
			log.error(ex.getMessage());
		}
		ResponseEntity<Order> customerDetails = restTemplate.getForEntity("http://localhost:6600/customer/1002", Order.class);
		//update the order status in the database
		orderRepository.save(order);
		
	}
	
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
