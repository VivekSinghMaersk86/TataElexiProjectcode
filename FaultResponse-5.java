package com.tatasky.mcr.model.response;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FaultResponse implements Serializable{

	private static final long serialVersionUID = -774172331352582009L;

	 private Integer sid;

	 private Integer channelSid;

	 private Integer errorNo;

	 private Date txDate;

	 private Integer avCodeSid;

	 private String scheduled;

	 private String fileRefrence;

	 private Integer eventTypeSid;

	 private String eventParticular;

	 private Date timeIn;

	 private Date timeOut;

	 private String duration;

	 private Integer causeSid;

	 private String equipment;

	 private String actionTaken;

	 private String actionConfirm;

	 private String ticketStatus;

	 private String remarks;

	 private Date txTime;

	 private Date modificationDate;

	 private String modificationBy;

	 private Date createdDate;

	 private String createdBy;

	 private String ticket;

}