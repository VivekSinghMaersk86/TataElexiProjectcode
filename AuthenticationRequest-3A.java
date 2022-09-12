package com.tatasky.mcr.model.request;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author vikaschoudhary
 *
 */

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {

	private static final long serialVersionUID = 5445780496357645231L;
	private String username;
	private String password;

}
