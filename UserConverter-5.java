package com.tatasky.mcr.converters;

import com.tatasky.mcr.entity.User;
import com.tatasky.mcr.model.request.UserRequest;
import com.tatasky.mcr.model.response.UserResponse;
import com.tatasky.mcr.util.McrPasswordEncoder;
/**
 * 
 * @author vikaschoudhary
 *
 */
public class UserConverter {

	public static User mapUserRequestToUser(UserRequest userRequest) {
		User user = new User();
		user.setUserName(userRequest.getUserName());
		user.setPassword(new McrPasswordEncoder().encode(userRequest.getPassword()));
		user.setEnabled(userRequest.isEnabled());
		user.setRoles(userRequest.getRoles());
		return user;
	}
	
	public static UserResponse mapUserToUserResponse(User user) {
		UserResponse userResponse = new UserResponse();
		userResponse.setSid(user.getId());
		userResponse.setUserName(user.getUserName());
		userResponse.setEnabled(user.isEnabled());
		userResponse.setRoles(user.getRoles());
		return userResponse;
	}
	
}
