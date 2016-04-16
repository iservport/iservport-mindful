package com.iservport.mindful.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;

import org.helianto.core.domain.Entity;
import org.helianto.core.domain.Identity;
import org.helianto.user.domain.User;

/**
 * Parlamentar.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@DiscriminatorValue("P")
public class Parlamentar extends User {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Campos
	 */
	
	@Column(length=4)
	private String legislatura;
	
	/**
	 * Constructor.
	 */
	public Parlamentar() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param entity
	 * @param identity
	 */
	public Parlamentar(Entity entity, Identity identity) {
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
	public Parlamentar(Entity entity, Identity identity, String legislatura) {
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
