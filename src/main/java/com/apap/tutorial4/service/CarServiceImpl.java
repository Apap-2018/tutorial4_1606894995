package com.apap.tutorial4.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tutorial4.model.CarModel;
import com.apap.tutorial4.model.DealerModel;
import com.apap.tutorial4.repository.CarDb;

@Service
@Transactional
public class CarServiceImpl implements CarService{
	@Autowired
	private CarDb carDb;

	@Override
	public void addCar(CarModel car) {
		carDb.save(car);
		
	}

	@Override
	public void deleteCar(CarModel car) {
		carDb.delete(car);
	}
	
	@Override
	public Optional<CarModel> getCarDetailById(Long id) {
		return carDb.findById(id);
	}

	@Override
	public void deleteAllCar(List<CarModel> list) {
		carDb.deleteAll(list);
	}

	@Override
	public void updateCar(CarModel car) {
		carDb.save(car);
		
	}

	
}
