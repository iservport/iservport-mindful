package com.iservport.mindful.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iservport.mindful.domain.Voto;

/**
 * Estatísticas.
 * 
 * @author mauriciofernandesdecastro
 */
public interface StatsRepository 
	extends JpaRepository<Voto, Serializable> {

}
