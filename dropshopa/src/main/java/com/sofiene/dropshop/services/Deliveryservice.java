package com.sofiene.dropshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sofiene.dropshop.models.Delivery;
import com.sofiene.dropshop.repository.Deliveryinterface;

@Service
public class Deliveryservice {
	private Deliveryinterface  deliveryinterface;
	 
	 
	 
	 public  Deliveryservice(Deliveryinterface deliveryinterface) {
	        this.deliveryinterface = deliveryinterface;
	    }
	    
	 public List<Delivery> alldeliverys() {
	        return deliveryinterface.findAll();
	    }
	    
	    public Delivery createDelivery(Delivery b) {
	        return deliveryinterface.save(b);
	    }
	    
	    // read one 
	    public Delivery findDelivery(Long id) {
	        Optional<Delivery> optionalShow= deliveryinterface.findById(id);
	        if(optionalShow.isPresent()) {
	            return optionalShow.get();
	        } else {
	            return null;
	        }
	    }
	    
	    
	   public Delivery updateDelivery(Delivery delivery) {
			
			return deliveryinterface.save(delivery);
		}
		
		public void deleteDelivery(Long id) {
			deliveryinterface.deleteById(id);
		}

}

