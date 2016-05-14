package com.iservport.mindful.repository;

import java.text.DecimalFormat;

/**
 * Classe para retornar detalhes da Votação de um documento(projeto).
 * 
 * @author Eldevan Nery Junior
 *
 */
public class VoteDetails {

	private Long agree = 0L;
	
	private Long disagree = 0L;
	
	private Long total = 1L;

	public VoteDetails(Long agree, Long disagree, Long total) {
		this.agree = agree;
		this.disagree = disagree;
		this.total = total;
	}

	public Long getAgree() {
		return agree;
	}

	public Long getDisagree() {
		return disagree;
	}

	public Long getTotal() {
		return total;
	}

	public void setAgree(Long agree) {
		this.agree = agree;
	}

	public void setDisagree(Long disagree) {
		this.disagree = disagree;
	}

	public void setTotal(Long total) {
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
