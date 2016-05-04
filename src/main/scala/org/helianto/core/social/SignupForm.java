package org.helianto.core.social;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;

/**
 * Adaptador para criação de usuários.
 * 
 * @author mauriciofernandesdecastro
 */
public class SignupForm {

	private String principal = "";

	private String password = "";

	@NotEmpty
	private String firstName = "";

	@NotEmpty
	private String lastName = "";

	private Integer entityId = 0;

	private String principalOfHead = "";

	private String familyRole = "HEAD";
	
	private String providerUserId = "";
	
	private Boolean licenseAccepted = false;
	
	private String imgUrl = "";

	public SignupForm() {
		super();
	}

	/**
	 * Form constructor.
	 *
	 * @param principal
	 * @param firstName
	 * @param lastName
     */
	public SignupForm(String principal, String firstName, String lastName) {
		this();
		this.principal = principal;
		this.firstName = firstName;
		this.lastName = lastName;
	}

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
	
	/**
	 * Nome completo.
	 */
	public String getFullName() {
		return new StringBuilder(getFirstName().trim()).append(" ").append(getLastName().trim()).toString();
	}
	
	public Integer getEntityId() {
		return entityId;
	}
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}
	
	public String getPrincipalOfHead() {
		return principalOfHead;
	}
	public void setPrincipalOfHead(String principalOfHead) {
		this.principalOfHead = principalOfHead;
	}
	
	public String getFamilyRole() {
		return familyRole;
	}
	public void setFamilyRole(String familyRole) {
		this.familyRole = familyRole;
	}
	
	public Boolean isLicenseAccepted() {
		return licenseAccepted;
	}
	
	public void setLicenseAccepted(Boolean licenseAccepted) {
		this.licenseAccepted = licenseAccepted;
	}
	
	public String getProviderUserId() {
		return providerUserId;
	}
	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public static SignupForm fromProviderUser(Connection<?> connection) {
		UserProfile userProfile = connection.fetchUserProfile();
		ConnectionData data = connection.createData();
		SignupForm form = new SignupForm();
		String email = userProfile.getEmail();
		String firstName = userProfile.getFirstName();
		String lastName = userProfile.getLastName();
		String img = data.getImageUrl();
		form.setProviderUserId(data.getProviderUserId());
		form.setFirstName(firstName!=null && !firstName.isEmpty()?firstName:form.getFirstName());
		form.setLastName(lastName!=null && !lastName.isEmpty() ? lastName: form.getLastName());
		form.setPrincipal(email!=null && !email.isEmpty()? email:form.getPrincipal());
		form.setImgUrl(img!=null && !img.isEmpty()?img:form.getImgUrl());
		return form;
	}

	@Override
	public String toString() {
		return "SignupForm [principal=" + principal + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", entityId=" + entityId + "]";
	}
	
	
}