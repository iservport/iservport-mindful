package com.iservport.mindful.domain;

import org.helianto.core.domain.Entity;
import org.helianto.core.domain.Feature;
import org.helianto.core.internal.AbstractTrunkEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Projeto legislativo.
 * 
 * @author Mauricio Fernandes de Castro
 */
@javax.persistence.Entity
@Table(name="mndf_document",
    uniqueConstraints = {@UniqueConstraint(columnNames={"entityId", "docCode"})}
)
public class LegalDocument extends AbstractTrunkEntity {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Chaves
	 */
	
	@Column(length=20)
    private String docCode;
	
	/*
	 * Campos
	 */
	
	@Column(length=256)
    private String docName;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date nextCheckDate;

	@Lob
    private byte[] docAbstract;
	
	@ManyToOne
	@JoinColumn(name="featureId")
    private Feature feature;
	
	@Lob
    private byte[] content;
	
	@ManyToOne
	@JoinColumn(name="authorId")
    private Parlamentar author;
	
    private Character resolution;
    
    /**
	 * Constructor.
	 */
	public LegalDocument() {
		super();
	}
	
	/**
	 * Constructor.
	 * 
	 * @param entity
	 * @param docCode
	 */
	public LegalDocument(Entity entity, String docCode) {
		this();
		setEntity(entity);
		setDocCode(docCode);
	}

	public String getDocCode() {
		return docCode;
	}
	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}

	public byte[] getDocAbstract() {
		return docAbstract;
	}
	public void setDocAbstract(byte[] docAbstract) {
		this.docAbstract = docAbstract;
	}

	public Feature getFeature() {
		return feature;
	}
	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}

	public Parlamentar getAuthor() {
		return author;
	}
	public void setAuthor(Parlamentar author) {
		this.author = author;
	}
	
	public Character getResolution() {
		if (getResolution()==null) {
			return 'T';
		}
		return resolution;
	}
	public void setResolution(Character resolution) {
		this.resolution = resolution;
	}
	
	public Date getNextCheckDate() {
		return nextCheckDate;
	}
	public void setNextCheckDate(Date nextCheckDate) {
		this.nextCheckDate = nextCheckDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getEntity() == null) ? 0 : getEntity().hashCode());
		result = prime * result + ((docCode == null) ? 0 : docCode.hashCode());
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
		LegalDocument other = (LegalDocument) obj;
		if (getEntity() == null) {
			if (other.getEntity() != null)
				return false;
		} else if (!getEntity().equals(other.getEntity()))
			return false;
		if (docCode == null) {
			if (other.docCode != null)
				return false;
		} else if (!docCode.equals(other.docCode))
			return false;
		return true;
	}
	
	

}
