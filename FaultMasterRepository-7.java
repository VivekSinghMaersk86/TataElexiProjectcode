package com.tatasky.mcr.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tatasky.mcr.entity.Fault;
import com.tatasky.mcr.model.request.CloseRequest;
import com.tatasky.mcr.model.response.FaultResponse;

@Repository
public interface FaultMasterRepository  extends JpaRepository<Fault, Integer>{

	FaultResponse save(CloseRequest closeRequest);

	//List<Fault> getAllDFRReport();

	
}