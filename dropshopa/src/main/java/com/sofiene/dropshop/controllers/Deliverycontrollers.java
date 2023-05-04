package com.sofiene.dropshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sofiene.dropshop.models.Delivery;
import com.sofiene.dropshop.models.User;
import com.sofiene.dropshop.services.Deliveryservice;
import com.sofiene.dropshop.services.Productservice;
import com.sofiene.dropshop.services.Userservice;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class Deliverycontrollers {
	@Autowired
	private Deliveryservice deliveryservice;
	@Autowired
	private Userservice userServ;
	@Autowired
	private Productservice productservice;
	
	
	@GetMapping("/home")
    public String newBabe(@ModelAttribute("delivery") Delivery  delivery, Model model,HttpSession s) {
		
		Long userId = (Long) s.getAttribute("user_id");
		
		// route guard
		if(userId == null) {
			return "redirect:/";
		}
		String userName = (String) s.getAttribute("userName");
		System.out.println("********");
        System.out.println(userName);
		
		List<Delivery> deliverys=deliveryservice.alldeliverys();
		model.addAttribute("deliverys",deliverys);
        return "showsall.jsp";
    }
	
	
	
	@GetMapping("/names/new")
	public String create(@ModelAttribute("delivery") Delivery delivery,Model model) {
		
		return "formdelivery.jsp";
	}
	
	@PostMapping("/create")
    public String create(@Valid @ModelAttribute("delivery") Delivery delivery, BindingResult result,Model model,
    		HttpSession session,RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
        	List<Delivery> deliverys=deliveryservice.alldeliverys();
    		model.addAttribute("deliverys",deliverys);
    		
            return "formdelivery.jsp";
        } else {
        	
        	Long userId = (Long) session.getAttribute("user_id");
//        	System.out.println(userId);
        	// Find user by ID
        	User currentUser = userServ.findUserById(userId);
        	
        	delivery.setUser(currentUser);
        	// Create a book in the DB
            deliveryservice.createDelivery(delivery);
            redirectAttributes.addFlashAttribute("message", "you succcefuly create a book");
            return "redirect:/home";
        }
    }
	 @GetMapping("/names/{id}")
		public String index(Model model,@PathVariable("id") Long id) {
			Delivery delivery =deliveryservice.findDelivery(id);
			model.addAttribute("delivery", delivery);

			return "Showonedelivery.jsp";
		}
	 @GetMapping("/names/edit/{id}")
		public String updateBabe(@PathVariable("id") Long babyId, Model model) {
			Delivery delivery= deliveryservice.findDelivery(babyId);
			model.addAttribute("delivery", delivery);
			return "edit.jsp";
		}
	 @RequestMapping(value="/names/{id}", method=RequestMethod.PUT)
	    public String update(@Valid @ModelAttribute("delivery") Delivery delivery, BindingResult result) {
	        if (result.hasErrors()) {
	            return "editdelivery.jsp";
	        } else {
	        	// Fetch the book object from DB
	        	Delivery orginalShow = deliveryservice.findDelivery(delivery.getId());
	        	delivery.setUser(orginalShow.getUser());
	        	
	            deliveryservice.updateDelivery(delivery);
	            return "redirect:/home";
	        }
	    }
	 @DeleteMapping("/names/{id}")
	    public String destroy(@PathVariable("id") Long id) {
	        deliveryservice.deleteDelivery(id);
	        return "redirect:/home";
	    }



	public Productservice getProductservice() {
		return productservice;
	}



	public void setProductservice(Productservice productservice) {
		this.productservice = productservice;
	}
	

}
