package com.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.orderservice.entity.Order;


public interface OrderRepository extends JpaRepository<Order,String> {

	//@Query represent custom query in JPQL(JPA query language)
	 @Query("FROM Order where customerId=?1")
	 List<Order> findOrdersByCustomerId(String customerId);
	 
	 @Query("FROM Order where customerId=?1 and productId=?2")
	 List<Order> findOrdersByCustomerIdAndProduct(String customerId, String productId);
}