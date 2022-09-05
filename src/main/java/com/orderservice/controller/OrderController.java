package com.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.orderservice.entity.Order;
import com.orderservice.service.OrderService;

@RestController
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@PostMapping("/order")
	public ResponseEntity<String> createOrder(@RequestBody Order order) {
		String response = orderService.createOrder(order);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@GetMapping("/orders")
	public List<Order> getOrders() {
		return orderService.getOrders();
	}
	
	@GetMapping("/orders/healthcheck")
	public String getHealthCheck() {
		return "service is up";
	}

}
