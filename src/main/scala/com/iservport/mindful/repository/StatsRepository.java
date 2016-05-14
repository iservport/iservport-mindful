package com.iservport.mindful.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iservport.mindful.domain.Vote;

/**
 * Estatísticas.
 * 
 * @author mauriciofernandesdecastro
 */
public interface StatsRepository 
	extends JpaRepository<Vote, Serializable> {

}
