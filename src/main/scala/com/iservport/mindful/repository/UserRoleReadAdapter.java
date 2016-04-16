package com.iservport.mindful.repository;

import java.io.Serializable;

import org.helianto.core.domain.Service;
import org.helianto.user.domain.User;
import org.helianto.user.domain.UserRole;


/**
 * Adaptador para leitura de papéis de usuário.
 *
 * @author mauriciofernandesdecastro
 */
public class UserRoleReadAdapter
	implements Serializable
{

	private static final long serialVersionUID = 1L;

	private UserRole adaptee ;

	private Integer id;

	private Integer userId;

	private Integer serviceId;

	private String serviceName;

	private String serviceExtension;

	private Character activityState;

	private String partnershipExtension;

	/**
	 * Construtor.
	 *
	 * @param id
	 * @param userId;
	 * @param serviceId;
	 * @param serviceName;
	 * @param serviceExtension;
	 * @param activityState;
	 * @param partnershipExtension;
	 */
	public UserRoleReadAdapter(
		Integer id
		, Integer userId
		, Integer serviceId
		, String serviceName
		, String serviceExtension
		, Character activityState
		, String partnershipExtension
		) {
		super();
		this.id = id;
		this.userId = userId;
		this.serviceId = serviceId;
		this.serviceName = serviceName;
		this.serviceExtension = serviceExtension;
		this.activityState = activityState;
		this.partnershipExtension = partnershipExtension;
	}

	/**
	 * Adaptee contructor.
	 *
	 * @param adaptee
	 */
	public UserRoleReadAdapter(UserRole userRole) {
		super();
		this.adaptee = userRole;
	}

	/**
	 * Adaptee builder.
	 * 
	 * @param user
	 * @param service
	 */
	public UserRoleReadAdapter build(User user, Service service){
		if (adaptee==null) {
			throw new RuntimeException("Null userRole cannot be persisted.");
		}
		return new UserRoleReadAdapter(adaptee.getId()
		, user.getId()
		, service.getId()
		, service.getServiceName()
		, adaptee.getServiceExtension()
		, adaptee.getActivityState()
		, adaptee.getPartnershipExtension()
		);
	}

	public UserRole getAdaptee() {
		return adaptee;
	}

	public Integer getId() {
		return id;
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public String getServiceName() {
		return serviceName;
	}

	public String getServiceExtension() {
		return serviceExtension;
	}

	public Character getActivityState() {
		return activityState;
	}

	public String getPartnershipExtension() {
		return partnershipExtension;
	}

	@Override
	public int hashCode() {
		return 31 + ((id == null) ? 0 : id.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		UserRoleReadAdapter other = (UserRoleReadAdapter) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

}

	// FIM da classe
