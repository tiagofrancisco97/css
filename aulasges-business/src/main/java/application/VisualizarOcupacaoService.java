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
	public List<facade.dto.Aula> visualizarOcupacao(String instalacao, Calendar data) throws ApplicationException {
		
		Calendar d= (Calendar) data.clone();
		return this.viasualizarOcupacaoHandler.visualizarOcupacao(instalacao, d);
	}
	
	public List<String> instalacoes() throws ApplicationException{
		return this.viasualizarOcupacaoHandler.instalacoes();
	}
	
}
