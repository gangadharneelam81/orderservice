package com.orderservice.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.orderservice.OrderserviceApplication;
import com.orderservice.entity.Order;
import com.orderservice.repository.OrderRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=OrderserviceApplication.class,webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderControllerTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@MockBean
	OrderRepository orderRepository;
	
	@Test
	public void createOrder()
	{
		HttpHeaders header = new HttpHeaders();
		header.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		String resVer = "order created 102";
		
		UriComponents builder = UriComponentsBuilder.fromHttpUrl("http://localhost:"+port+"/order").build();
		Order order = new Order();
		order.setCustomerId("1002");
		order.setOrderAmt(100.2);
		order.setOrderId("102");
		order.setProductId("10002");
		order.setOrderDesc("Test");
		Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
		HttpEntity<?> entity = new HttpEntity<>(order,header);
		ResponseEntity<String> responseEntity = this.testRestTemplate.postForEntity(builder.toString(), entity, String.class);
		String res = responseEntity.getBody();
		Assertions.assertNotNull(responseEntity);
		Assertions.assertEquals(resVer, res);
		
	}
}
