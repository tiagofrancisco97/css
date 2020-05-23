package application;

import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.handlers.VisualizarOcupacaoHandler;
import facade.exceptions.ApplicationException;
import facade.handlers.IVisualizarOcupacaoServiceRemote;

@Stateless
public class VisualizarOcupacaoService implements IVisualizarOcupacaoServiceRemote {
	
	@EJB
	private VisualizarOcupacaoHandler viasualizarOcupacaoHandler;
	
	
	/**
     * Constructs a visualizar ocupacao service given the visualizar ocupacao handler.
     * 
     * @param inscreverAulaHandler 
     */
	/*public VisualizarOcupacaoService(VisualizarOcupacaoHandler viasualizarOcupacaoHandler) {
		this.viasualizarOcupacaoHandler = viasualizarOcupacaoHandler;
	}*/
	
	/**
	 * Obtem aulas numda dada instala��o
	 * @param instalacao nome da instala��o
	 * @param data data atual
	 * @return lista de entradas com as aulas que decorrem na instala��o
	 * @throws ApplicationException
	 */
	public List<facade.dto.Aula> visualizarOcupacao(String instalacao, String data) throws ApplicationException {
		int dia = Integer.parseInt(data.split("/")[0]);
		int mes = Integer.parseInt(data.split("/")[1]);
		int ano = Integer.parseInt(data.split("/")[2]);
		Calendar d= Calendar.getInstance();
		d.set(Calendar.DAY_OF_MONTH, dia);
		d.set(Calendar.MONTH, mes);
		d.set(Calendar.YEAR, ano);
		return this.viasualizarOcupacaoHandler.visualizarOcupacao(instalacao, d);
	}
	
}
