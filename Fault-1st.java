package com.tatasky.mcr.entity;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="FAULT")
public class Fault implements Serializable {
	
	private static final long serialVersionUID = 3073344364951125659L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @Column(name="SID")
    private Integer sid;
	
	@Basic(optional = false)
	@Column(name="CHANNEL_SID")
	private Integer channelSid;

	@Column(name="ERROR_NO")
	private Integer errorNo;
	  
	@Column(name="TXDATE")
	private Date txDate;

	 @Basic(optional = false)
	 @Column(name="AV_CODE_SID")
	 private Integer avCodeSid;
	 
	 @Column(name="SCH")
	 private String scheduled;

	 @Column(name="FILE_REFRENCE")
	 private String fileRerence;
	 
	 @Column(name="EVENT_TYPE_SID")
	 private Integer eventTypeSid;

	 @Column(name="EVENT_PARTICULAR")
	 private String eventParticular;
	 
	 @Column(name="TIME_IN")
	 private Date timeIn;
	 
	 @Column(name="TIME_OUT")
	 private Date timeOut;
	 
	 @Column(name="DURATION")
	 private String duration;

	 @Column(name="CAUSE_SID")
	 private Integer causeSid;

	 @Column(name="EQUIPMENT")
	 private String equipment;
	    
	 @Column(name="ACTION_TAKEN")
	 private String actionTaken;

	 @Column(name="ACTION_CONFIRM")
	 private String actionConfirm;

	 @Column(name="TICKET_STATUS")
	 private String ticketStatus;
	  
	 @Column(name="REMARKS")
	 private String remarks;

	 @Column(name="TXTIME")
	 private Date txTime;

	 @Column(name="MODIFIED_DATE")
	 private Date modificationDate;
	    
	 @Column(name="MODIFIED_BY")
	 private String modificationBy;
	 
	 @Basic(optional = false)
	 @Column(name="CREATED_DATE")
	 private Date createdDate;
     
	 @Basic(optional = false)
	 @Column(name="CREATED_BY")
	 private String createdBy;
	    
	 @Column(name="TICKET")
	 private String ticket;
	 
	 @OneToOne
	 @JoinColumn(name="CHANNEL_SID" , insertable = false, updatable = false)
	 private Channel channel;
	 
	 @OneToOne
	 @JoinColumn(name="CAUSE_SID"  , insertable = false, updatable = false)
	 private Cause cause;
	 
	 @OneToOne
	 @JoinColumn(name="EVENT_TYPE_SID" , insertable = false, updatable = false)
	 private EventType eventtype;
	 
	 @OneToOne
	 @JoinColumn(name="AV_CODE_SID"  , insertable = false, updatable = false)
	 private AvCode avCode;

	 public Fault() {}

	public Fault(Integer sid) {
		super();
		this.sid = sid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fault other = (Fault) obj;
		return Objects.equals(sid, other.sid);
	}  
	
    }
