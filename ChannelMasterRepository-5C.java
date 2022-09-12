package com.tatasky.mcr.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tatasky.mcr.entity.Channel;
/**
 * 
 * @author vikaschoudhary
 *
 */
@Repository
public interface ChannelMasterRepository  extends JpaRepository<Channel, Integer>{
	
}