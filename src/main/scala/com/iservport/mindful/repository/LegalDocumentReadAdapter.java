package com.iservport.mindful.repository;

import java.util.Date;

/**
 * Legal document read adapter.
 */
public class LegalDocumentReadAdapter {
	
	private Integer id;

    private String docCode;
	
    private String docName;
	
    private Date nextCheckDate;

    private byte[] docAbstract;
    
    private Character resolution = 'T';
	
    private Integer featureId;
	
    private byte[] content;
	
    private Integer authorId;
    
    private String featureName;
    
    private String authorName;
    
    private Boolean vote = true;
	
    /**
	 * Constructor.
	 */
	public LegalDocumentReadAdapter() {
		super();
	}
	
	

	public LegalDocumentReadAdapter(Integer id
			, String docCode
			, String docName
			, Date nextCheckDate
			, byte[] docAbstract
			, Character resolution
			, Integer featureId
			, byte[] content
			, Integer authorId
			, String featureName
			, String authorName) {
		this.id = id;
		this.docCode = docCode;
		this.docName = docName;
		this.nextCheckDate = nextCheckDate;
		this.docAbstract = docAbstract;
		this.resolution = resolution;
		this.featureId = featureId;
		this.content = content;
		this.authorId = authorId;
		this.featureName = featureName;
		this.authorName = authorName;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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

	public String getDocAbstract() {
		return new String(this.docAbstract!=null?this.docAbstract:new byte[0]);
	}
	public void setDocAbstract(byte[] docAbstract) {
		this.docAbstract = docAbstract;
	}

	public Character getResolution() {
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

	public Integer getFeatureId() {
		return featureId;
	}
	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
	}

	public String getContent() {
		return new String(this.content!=null?this.content:new byte[0]);
	}
	public void setContent(byte[] content) {
		this.content = content;
	}

	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}

	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	
	public Boolean isOpen(){
		return getResolution().equals('T') && getVote();
	}
	
	public Boolean isClosed(){
		return getResolution().equals('D');
	}
	
	public Boolean getVote() {
		return vote;
	}

	public void setVote(Boolean vote) {
		this.vote = vote;
	}
	
}
