package presentation.web.model;



import java.util.ArrayList;
import java.util.List;

import facade.exceptions.ApplicationException;
import facade.handlers.IinscreverAulaServiceRemote;

/**
 * Helper class to assist in the response of nova inscricao.
 * This class is the response information expert.
 * 
 *
 */
public class InscricaoModel extends Model {

	private String modalidade;
	private String inscricao;
	private String aula;
	private String numeroUtente;
	private boolean botao = false;
	private IinscreverAulaServiceRemote inscreverAulaHandler;
	
	public void setInscreverAulaHandler(IinscreverAulaServiceRemote inscreverAulaHandler) {
		this.inscreverAulaHandler = inscreverAulaHandler;
	}
	
	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
		this.botao = true;
	}

	public String getInscricao() {
		return inscricao;
	}

	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
	}

	public String getAula() {
		return aula;
	}

	public void setAula(String aula) {
		this.aula = aula;
	}

	public String getNumeroUtente() {
		return numeroUtente;
	}

	public void setNumeroUtente(String numeroUtente) {
		this.numeroUtente = numeroUtente;
	}

	

	public void clearFields() {
		modalidade = inscricao = aula = "";
		numeroUtente = "0";
	}
	
	public List<String> getModalidades () {
		try {
			return this.inscreverAulaHandler.iniciaInscricaoAula();
		} catch (ApplicationException e) {
			return new ArrayList<>();		
		}
	}
	
	public List<facade.dto.Aula> getAulasAtivas () {
		try {
			return this.inscreverAulaHandler.aulasAtivas(this.modalidade, this.inscricao);
		} catch (ApplicationException e) {
			return new ArrayList<>();		
		}
	}	
}