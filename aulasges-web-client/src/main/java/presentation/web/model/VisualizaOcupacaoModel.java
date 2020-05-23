package presentation.web.model;


import java.util.LinkedList;

import facade.exceptions.ApplicationException;
import facade.handlers.IVisualizarOcupacaoServiceRemote;

/**
 * Helper class to assist in the response of novo cliente.
 * This class is the response information expert.
 * 
 * @author fmartins
 *
 */
public class VisualizaOcupacaoModel extends Model {

	private String instalacao;
	private String data;
	private IVisualizarOcupacaoServiceRemote visualizarOcupacaoHandler;;
		
	public void setAddCustomerHandler(
			IVisualizarOcupacaoServiceRemote visualizarOcupacaoHandler) {
		this.visualizarOcupacaoHandler = visualizarOcupacaoHandler;
	}
	
	public String getInstalacao() {
		return instalacao;
	}



	public void setInstalacao(String instalacao) {
		this.instalacao = instalacao;
	}



	public String getData() {
		return data;
	}



	public void setData(String data) {
		this.data = data;
	}



	public void clearFields() {
		
	}
	
	public Iterable<facade.dto.Aula> getDiscounts () {
		try {
			return this.visualizarOcupacaoHandler.visualizarOcupacao(instalacao, data);
		} catch (ApplicationException e) {
			return new LinkedList<facade.dto.Aula> ();		
		}
	}
}
