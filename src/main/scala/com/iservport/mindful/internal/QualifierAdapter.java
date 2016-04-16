package com.iservport.mindful.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.helianto.core.domain.Category;
import org.helianto.core.internal.KeyNameAdapter;
import org.helianto.core.internal.SimpleCounter;
import org.helianto.core.repository.CategoryReadAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Adaptador de qualificação.
 * 
 * @author mauriciofernandesdecastro
 */
public class QualifierAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(QualifierAdapter.class);

	private KeyNameAdapter qualifier;
	
	private int countItems = 0;
	
	private int countAlerts = 0;
	
	private int countWarnings = 0;
	
	private int countOthers = 0;
	
	/**
	 * Construtor.
	 */
	public QualifierAdapter() {
		super();
	}

	/**
	 * Construtor.
	 * 
	 * @param qualifier
	 */
	public QualifierAdapter(KeyNameAdapter qualifier) {
		this();
		setQualifier(qualifier);
	}

	/**
	 * Qualificador.
	 */
	@JsonIgnore
	public KeyNameAdapter getQualifier() {
		return qualifier;
	}
	public void setQualifier(KeyNameAdapter qualifier) {
		this.qualifier = qualifier;
	}

	/**
	 * Chave do qualificador.
	 */
	public Serializable getQualifierValue() {
		if (qualifier==null) {
			return new Integer(0);
		}
		return qualifier.getKey();
	}
	
	/**
	 * Nome do qualificador.
	 */
	public String getQualifierName() {
		if (qualifier==null) {
			return null;
		}
		return qualifier.getName();
	}
	
	/**
	 * Contagem de itens.
	 */
	public int getCountItems() {
		return countItems;
	}
	public void setCountItems(int countItems) {
		this.countItems = countItems;
	}

	public QualifierAdapter setCountItems(List<SimpleCounter> counterList) {
		for (SimpleCounter counter: counterList) {
			if (match(counter)) {
				setCountItems((int) counter.getItemCount());
				logger.debug("Found {} items.", counter.getItemCount());
				break;
			}
		}
		return this;
	}
	
	/**
	 * Verdadeiro se baseClass contém uma chave igual ao valor do qualificador.
	 * 
	 * @param counter
	 */
	protected boolean match(SimpleCounter counter) {
		return  (counter.getBaseClass()!=null 
					&& counter.getBaseClass().equals(getQualifierValue())) 
				||
				(counter.getBaseClass()==null 
					&& getQualifierValue() instanceof Integer
					&& getQualifierValue().equals(new Integer(0)))
				||
				(counter.getBaseClass()==null 
					&& getQualifierValue() instanceof Character
					&& getQualifierValue().equals(new Character('_')));
	}
	
	/**
	 * Método auxiliar para criar uma lista de adaptadores.
	 * 
	 * @param qualifierArray
	 */
	public static List<QualifierAdapter> qualifierAdapterList(KeyNameAdapter[] qualifierArray) {
		List<QualifierAdapter> qualifierAdapterList = new ArrayList<>();
		for (KeyNameAdapter qualifier: qualifierArray) {
			qualifierAdapterList.add(new QualifierAdapter(qualifier));
		}
		return qualifierAdapterList;
	}
	
	/**
	 * Método auxiliar para criar uma lista de adaptadores.
	 * 
	 * @param categoryList
	 */
	public static List<QualifierAdapter> qualifierAdapterList(List<Category> categoryList) {
		return qualifierAdapterList(categoryList, true);
	}
	
	/**
	 * Método auxiliar para criar uma lista de adaptadores.
	 * 
	 * @param categoryList
	 * @param addEmpty
	 */
	public static List<QualifierAdapter> qualifierAdapterList(List<Category> categoryList, boolean addEmpty) {
		List<QualifierAdapter> qualifierAdapterList = new ArrayList<>();
		for (Category qualifier: categoryList) {
			qualifierAdapterList.add(new QualifierAdapter(qualifier));
		}
		if (addEmpty) {
			qualifierAdapterList.add(new QualifierAdapter());
		}
		return qualifierAdapterList;
	}
	
	/**
	 * Método auxiliar para criar uma lista de adaptadores.
	 * 
	 * @param categoryAdapterList
	 * @param addEmpty
	 */
	public static List<QualifierAdapter> categoryAdapterList(List<CategoryReadAdapter> categoryAdapterList, boolean addEmpty) {
		List<QualifierAdapter> qualifierAdapterList = new ArrayList<>();
		for (KeyNameAdapter qualifier: categoryAdapterList) {
			qualifierAdapterList.add(new QualifierAdapter(qualifier));
		}
		if (addEmpty) {
			qualifierAdapterList.add(new QualifierAdapter());
		}
		return qualifierAdapterList;
	}
	
	/**
	 * Contagem de alertas.
	 */
	public int getCountAlerts() {
		return countAlerts;
	}
	public void setCountAlerts(int countAlerts) {
		this.countAlerts = countAlerts;
	}

	public QualifierAdapter setCountAlerts(List<SimpleCounter> counterList) {
		for (SimpleCounter counter: counterList) {
			if (match(counter)) {
				setCountAlerts((int) counter.getItemCount());
				logger.debug("Found {} alerts.", counter.getItemCount());
				break;
			}
		}
		return this;
	}
	
	/**
	 * Contagem de avisos.
	 */
	public int getCountWarnings() {
		return countWarnings;
	}
	public void setCountWarnings(int countWarnings) {
		this.countWarnings = countWarnings;
	}
	public QualifierAdapter setCountWarnings(List<SimpleCounter> counterList) {
		for (SimpleCounter counter: counterList) {
			if (match(counter)) {
				setCountWarnings((int) counter.getItemCount());
				logger.debug("Found {} warnings.", counter.getItemCount());
				break;
			}
		}
		return this;
	}
	
	public int getCountOthers() {
		return countOthers;
	}
	public void setCountOthers(int countOthers) {
		this.countOthers = countOthers;
	}
	public QualifierAdapter setCountOthers(List<SimpleCounter> counterList) {
		for (SimpleCounter counter: counterList) {
			if (counter.getBaseClass()==getQualifierValue()) {
				setCountOthers((int) counter.getItemCount());
				logger.debug("Found {} others.", counter.getItemCount());
				break;
			}
		}
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((qualifier == null) ? 0 : qualifier.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof QualifierAdapter)) {
			return false;
		}
		QualifierAdapter other = (QualifierAdapter) obj;
		if (qualifier == null) {
			if (other.qualifier != null) {
				return false;
			}
		} else if (!qualifier.equals(other.qualifier)) {
			return false;
		}
		return true;
	}

}
