package com.iservport.network.service;

import org.helianto.core.internal.KeyNameAdapter;

import java.io.Serializable;

public enum MindfulNetworkKeyName implements KeyNameAdapter
{

	CITY_HOUSE('C'),
	STATE_HOUSE('S'),
	LOWER_HOUSE('L'),
	UPPER_HOUSE('U');

	private char value;

	/**
	 * Construtor.
	 *
	 * @param value
	 */
	private MindfulNetworkKeyName(char value) {
		this.value = value;
	}
	
	public Serializable getKey() {
		return this.value;
	}
	
	@Override
	public String getCode() {
		return value+"";
	}
	
	@Override
	public String getName() {
		return name();
	}
	
	public KeyNameAdapter[] getValues() {
		return values();
	}
	
}

