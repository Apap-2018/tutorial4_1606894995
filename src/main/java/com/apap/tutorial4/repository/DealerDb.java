package com.apap.tutorial4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tutorial4.model.DealerModel;

@Repository
public interface DealerDb extends JpaRepository<DealerModel, Long>{
	
}
