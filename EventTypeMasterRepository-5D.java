package com.tatasky.mcr.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tatasky.mcr.entity.EventType;
/**
 * 
 * @author vikaschoudhary
 *
 */
@Repository
public interface EventTypeMasterRepository  extends JpaRepository<EventType, Integer>{

	//List<EventType> findAll();
	
}