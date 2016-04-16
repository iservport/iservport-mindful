package com.iservport.mindful.repository;

import java.text.DecimalFormat;

/**
 * Classe para retornar detalhes da Votação de um documento(projeto).
 * 
 * @author Eldevan Nery Junior
 *
 */
public class VotoDetails {

	private Integer agree = 0;
	
	private Integer disagree = 0;
	
	private Integer total = 1;

	public VotoDetails(Integer agree, Integer disagree, Integer total) {
		this.agree = agree;
		this.disagree = disagree;
		this.total = total;
	}

	public Integer getAgree() {
		return agree;
	}

	public Integer getDisagree() {
		return disagree;
	}

	public Integer getTotal() {
		return total;
	}

	public void setAgree(Integer agree) {
		this.agree = agree;
	}

	public void setDisagree(Integer disagree) {
		this.disagree = disagree;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	/**
	 * Retorna em porcentagem a aceitação do projeto.
	 * 
	 */
	public Double getAgreePerc(){
		return Double.parseDouble((
					//formata % para duas casas após a virgula
					new DecimalFormat("##.##").format(
							//porcentagem
							(getAgree()/(double)getTotal()) * 100))
					// muda ',' para '.' então parseDouble() ocorre corretamente.
					.replace(",", ".")
				); 
	}
	
	/**
	 * Retorna em porcentagem a não aceitação do projeto.
	 * 
	 */
	public Double getDisagreePerc(){
		return Double.parseDouble((new DecimalFormat("##.##").format((getDisagree()/(double)getTotal()) * 100)).replace(",", "."));
	}
	
}
