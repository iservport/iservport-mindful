package com.iservport.mindful.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;

import org.helianto.core.domain.Entity;
import org.helianto.core.domain.Identity;
import org.helianto.user.domain.User;

/**
 * Legislator.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@DiscriminatorValue("P")
public class Legislator extends User {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Campos
	 */
	
	@Column(length=4)
	private String legislatura;
	
	/**
	 * Constructor.
	 */
	public Legislator() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param entity
	 * @param identity
	 */
	public Legislator(Entity entity, Identity identity) {
		this();
		setEntity(entity);
		setIdentity(identity);
	}

	/**
	 * Constructor.
	 * 
	 * @param entity
	 * @param identity
	 * @param legislatura
	 */
	public Legislator(Entity entity, Identity identity, String legislatura) {
		this(entity, identity);
		setLegislatura(legislatura);
	}

	public String getLegislatura() {
		return legislatura;
	}
	public void setLegislatura(String legislatura) {
		this.legislatura = legislatura;
	}

}
