package com.iservport.mindful.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.helianto.user.domain.User;

/**
 * Vote.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name="mndf_vote",
    uniqueConstraints = {@UniqueConstraint(columnNames={"documentId", "userId"})}
)
public class Vote implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Keys
	 */
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
	
	@Version
	private Integer version;
	
	@ManyToOne
	@JoinColumn(name="documentId")
    private LegalDocument documento;
	
	@ManyToOne
	@JoinColumn(name="userId")
    private User eleitor;
	
    private Integer voto;
    
    /**
     * Constructor.
     */
    public Vote() {
		super();
	}
    
    /**
     * Constructor.
     * 
     * @param documento
     * @param eleitor
     */
    public Vote(LegalDocument documento, User eleitor) {
    	this();
    	setDocumento(documento);
		setEleitor(eleitor);
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

	public LegalDocument getDocumento() {
		return documento;
	}
	public void setDocumento(LegalDocument documento) {
		this.documento = documento;
	}

	public User getEleitor() {
		return eleitor;
	}
	public void setEleitor(User eleitor) {
		this.eleitor = eleitor;
	}

	public Integer getVoto() {
		return voto;
	}
	public void setVoto(Integer voto) {
		this.voto = voto;
	}
    
	public boolean isParlamentar() {
		return (getEleitor() instanceof Parlamentar);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eleitor == null) ? 0 : eleitor.hashCode());
		result = prime
				* result
				+ ((documento == null) ? 0 : documento
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vote other = (Vote) obj;
		if (eleitor == null) {
			if (other.eleitor != null)
				return false;
		} else if (!eleitor.equals(other.eleitor))
			return false;
		if (documento == null) {
			if (other.documento != null)
				return false;
		} else if (!documento.equals(other.documento))
			return false;
		return true;
	}

}
