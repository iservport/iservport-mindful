package com.iservport.mindful.domain;

import java.io.Serializable;

import org.helianto.core.internal.KeyNameAdapter;

/**
 * Define tipos de parlamento.
 * 
 * @author mauriciofernandesdecastro
 */
public enum TipoDeParlamento implements KeyNameAdapter {
	
	SENADO('S', 'F'),
	CAMARA_FEDERAL('C', 'F'),
	ESTADUAL('E', 'E'),
	MUNICIPAL('M', 'M');
	
	private char value;
	private char esfera;
	
	private TipoDeParlamento(char value, char esfera) {
		this.value = value;
		this.esfera = esfera;
	}
	
	private TipoDeParlamento(char esfera) {
		this(esfera, esfera);
	}
	
	public char getEsfera() {
		return esfera;
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
	
}
