package com.sofiene.dropshop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sofiene.dropshop.models.Delivery;

;

public interface Deliveryinterface extends CrudRepository<Delivery, Long>{
	List<Delivery> findAll(); 

}
