package com.tatasky.mcr.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tatasky.mcr.entity.AvCode;
/**
 * 
 * @author vikaschoudhary
 *
 */
@Repository
public interface AvCodeMasterRepository  extends JpaRepository<AvCode, Integer>{

	
}