package com.iservport.user.service;

import org.helianto.core.internal.KeyNameAdapter;

import java.io.Serializable;

public enum SimpleUserKeyName implements KeyNameAdapter
{
	
	ALL('A'),
	FUNCTION('F'),
	CONSULTANCY('Y'),
	SYSTEM('G');
	
	private char value;
	
	/**
	 * Construtor.
	 * 
	 * @param value
	 */
	private SimpleUserKeyName(char value) {
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

