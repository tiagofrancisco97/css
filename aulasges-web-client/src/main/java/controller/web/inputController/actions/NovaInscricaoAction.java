package controller.web.inputController.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import facade.handlers.IinscreverAulaServiceRemote;
import presentation.web.model.InscricaoModel;


/**
 * Handles the inscrever aula event.
 * If the request is valid, it dispatches it to the domain model (the application's business logic)
 * Notice as well the use of an helper class to assist in the jsp response. 
 * 
 *
 */
@Stateless
public class NovaInscricaoAction extends Action {

	@EJB private IinscreverAulaServiceRemote inscreverAulaHandler;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		InscricaoModel model = createHelper(request);
		request.setAttribute("model", model);
		request.setAttribute("modalidades", model.getModalidades());

		List<String> tipoInscricao = new ArrayList<>();
		tipoInscricao.add("Regular");
		tipoInscricao.add("Avulso");
		request.setAttribute("tipo_inscricoes", tipoInscricao);

		if (validInput(model)) {
			List <facade.dto.Aula> aulas = model.getAulasAtivas();
			request.setAttribute("aulas", aulas);
			//colocar no jsp uma lista select para escolher a aula pretendida
		
		} else {
			model.addMessage("Erro na validação dos dados da inscrição");
		}
		request.getRequestDispatcher("/inscricaoAula/novaInscricao.jsp").forward(request, response);
	}


	private boolean validInput(InscricaoModel model) {
		
		boolean result = isFilled(model, model.getInscricao(), "Tem de indicar o tipo de inscrição");
		result &= isFilled(model, model.getModalidade(), "Tem de indicar a modalidade");

		return result;
	}


	private InscricaoModel createHelper(HttpServletRequest request) {
		// Create the object model
		InscricaoModel model = new InscricaoModel();
		model.setInscreverAulaHandler(this.inscreverAulaHandler);

		// fill it with data from the request
		model.setAula(request.getParameter("aula"));
		model.setInscricao(request.getParameter("inscricao"));
		model.setModalidade(request.getParameter("modalidade"));
		
		return model;
	}	
}
