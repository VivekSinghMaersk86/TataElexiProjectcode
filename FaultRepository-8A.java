package com.tatasky.mcr.repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.TemporalType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.tatasky.mcr.entity.Channel;
import com.tatasky.mcr.entity.Fault;

/**
 * 
 * @author vikaschoudhary
 *
 */

@Repository
public interface FaultRepository extends JpaRepository<Fault, Integer> {

	List<Fault> findByTxDateBetween(@Temporal(TemporalType.DATE) @Param("startDate") Date startDate,
			@Temporal(TemporalType.DATE) @Param("endDate") Date endDate);
	
	List<Fault> findByChannelInAndTxDateBetween(@Param("channel") List<Channel> channel, @Temporal(TemporalType.DATE) @Param("startDate") Date startDate,
			@Temporal(TemporalType.DATE) @Param("endDate") Date endDate);
	
	Optional<Fault> findBySid(@Param("sid") Long sid);
}
