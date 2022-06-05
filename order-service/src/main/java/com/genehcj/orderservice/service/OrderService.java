package com.genehcj.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.genehcj.orderservice.dto.InventoryResponse;
import com.genehcj.orderservice.dto.OrderLineItemsDto;
import com.genehcj.orderservice.dto.OrderRequest;
import com.genehcj.orderservice.model.Order;
import com.genehcj.orderservice.model.OrderLineItems;
import com.genehcj.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

	private final OrderRepository orderRepository;
	private final WebClient.Builder webClientBuilder;
	
	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
			.map(this::mapToDto)
			.toList();
		
		order.setOrderLineItems(orderLineItems);
		
		List<String> skuCodes = order.getOrderLineItems().stream()
			.map(OrderLineItems::getSkuCode)
			.toList();
		
		InventoryResponse[] inventoryResponseArr = webClientBuilder.build()
			.get()
			.uri("http://inventory-service/api/inventory",
					uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
			.retrieve()
			.bodyToMono(InventoryResponse[].class)
			.block();
		
		Boolean allProductsInStock = Arrays
				.stream(inventoryResponseArr)
				.allMatch(InventoryResponse::getIsInStock);
		
		if (allProductsInStock) {
			orderRepository.save(order);
		} else {
			throw new IllegalArgumentException("Product is not in stock, please try again later");
		}
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemDto) {
		// TODO Auto-generated method stub
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemDto.getPrice());
		orderLineItems.setQuantity(orderLineItemDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemDto.getSkuCode());
		return orderLineItems;
	}
	
}
