package com.apap.tutorial4.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial4.model.CarModel;
import com.apap.tutorial4.model.DealerModel;
import com.apap.tutorial4.service.CarService;
import com.apap.tutorial4.service.DealerService;

@Controller
public class DealerController{
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("dealer", new DealerModel());
		return "addDealer";
	}
	
	@RequestMapping(value = "/dealer/delete/{dealerId}", method = RequestMethod.GET)
	private String delete(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		List<CarModel> myCar = dealer.getListCar();
		if(!myCar.isEmpty()) {
			for (CarModel car : dealer.getListCar()) {
				carService.deleteCar(car);
			}
		}
		
		dealerService.deleteDealer(dealer);
		return "delete";
	}
	
	
	@RequestMapping(value = "/dealer/view", method = RequestMethod.GET)
	private String viewDealer(@RequestParam(value = "dealerId") Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		List<CarModel> myCar = dealer.getListCar();
		Collections.sort(myCar, CarComparatorByPrice);
		model.addAttribute("allCar",myCar);
		model.addAttribute("dealer",dealer);
		return "view-dealer";
	}
	@RequestMapping(value = "/dealer/view/all", method = RequestMethod.GET)
	private String viewAllDealer(Model model) {
		List<DealerModel> myDealer = dealerService.viewAllDealer();
		model.addAttribute("dealer", myDealer);
		return "all-dealer";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer) {
		dealerService.addDealer(dealer);
		return "add";
	}
	
	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.GET)
	private String update(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		DealerModel newDealer = new DealerModel();
		newDealer.setId(dealer.getId());
		model.addAttribute("dealer",newDealer);
		model.addAttribute("oldDealer",dealer);
		return "updateDealer";
	}
	
	@RequestMapping(value = "/dealer/update", method = RequestMethod.POST)
	private String updateDealerSubmit(@ModelAttribute DealerModel dealer) {
		DealerModel real = dealerService.getDealerDetailById(dealer.getId()).get();
		real.setAlamat(dealer.getAlamat());
		real.setNoTelp(dealer.getNoTelp());
		dealerService.addDealer(real);
		return "update";
	}
	public static Comparator<CarModel> CarComparatorByPrice = new Comparator<CarModel>() {
		public int compare (CarModel o1, CarModel o2) {
			Long price1 = o1.getPrice();
			Long price2 = o2.getPrice();
			return price1.compareTo(price2);
		}
	};
	
}
