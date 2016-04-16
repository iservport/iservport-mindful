package com.iservport.mindful.social;

import org.springframework.social.connect.UserProfile;

/**
 * Adaptador para criação de usuários.
 * 
 * @author mauriciofernandesdecastro
 */
public class SignupForm {

	private String principal = "";

//	@Size(min = 6, message = "must be at least 6 characters")
	private String password = "";

	private String firstName = "";

	private String lastName = "";

	private Integer entityId = 0;

	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String username) {
		this.principal = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	public static SignupForm fromProviderUser(UserProfile providerUser) {
		SignupForm form = new SignupForm();
		String email = providerUser.getEmail();
		String firstName = providerUser.getFirstName();
		String lastName = providerUser.getLastName();
		form.setFirstName(firstName!=null && !firstName.isEmpty()?firstName:form.getFirstName());
		form.setLastName(lastName!=null && !lastName.isEmpty() ? lastName: form.getLastName());
		form.setPrincipal(email!=null && !email.isEmpty()? email:form.getPrincipal());
		return form;
	}
	@Override
	public String toString() {
		return "SignupForm [principal=" + principal + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", entityId=" + entityId + "]";
	}
	
	
}