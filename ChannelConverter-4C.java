package com.tatasky.mcr.converters;
import java.util.ArrayList;
import java.util.List;                                      
import com.tatasky.mcr.entity.Channel;
import com.tatasky.mcr.model.response.ChannelResponse;
import io.jsonwebtoken.lang.Collections;
/**
 * 
 * @author vikaschoudhary
 *
 */
public class ChannelConverter {

	public static List<ChannelResponse> mapChannelToChannelResponse(List<Channel> channellist) {
		
		List<ChannelResponse>  channelResponselist = new ArrayList<ChannelResponse>();
		
		if(Collections.isEmpty(channellist) ) {
			return channelResponselist;
		}
		
		    channellist.stream().forEach(channeli -> {
			
			ChannelResponse channelResponsei = new ChannelResponse();
	
			channelResponsei.setSid(channeli.getSid());
			channelResponsei.setChannelCode(channeli.getChannelCode());
			//channelResponsei.setBouquetSid(channeli.getBouquetSid());
			channelResponsei.setChannelName(channeli.getChannelName());
			channelResponsei.setActive(channeli.isActive());
			//channelResponsei.setIsChannel(channeli.isChannel());
			channelResponsei.setLaunchDate(channeli.getLaunchDate());
			channelResponsei.setParentChannelSid(channeli.getParentChannelSid());
			channelResponselist.add(channelResponsei);

		    } );
		
		return channelResponselist;
	}
	
	
}
