package com.tatasky.mcr.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tatasky.mcr.entity.Cause;
/**
 * 
 * @author vikaschoudhary
 *
 */
@Repository
public interface CauseMasterRepository  extends JpaRepository<Cause, Integer>{

	
}