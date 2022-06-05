package com.genehcj.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genehcj.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
