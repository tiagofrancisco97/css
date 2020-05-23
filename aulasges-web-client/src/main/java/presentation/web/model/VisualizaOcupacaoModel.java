package presentation.web.model;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
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
		
	public Iterable<String> getInstalacoes () {
		try {
			return this.visualizarOcupacaoHandler.instalacoes();
		} catch (ApplicationException e) {
			e.printStackTrace();
			return new LinkedList<String> ();		
		}
	}
}
