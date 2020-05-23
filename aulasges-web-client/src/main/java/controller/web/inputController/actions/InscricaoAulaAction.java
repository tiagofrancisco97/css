package controller.web.inputController.actions;

import java.io.IOException;


import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.exceptions.ApplicationException;
import facade.handlers.IinscreverAulaServiceRemote;
import presentation.web.model.InscricaoModel;

public class InscricaoAulaAction extends Action{
	@EJB private IinscreverAulaServiceRemote inscreverAulaHandler;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		InscricaoModel model = new InscricaoModel();
		model.setInscreverAulaHandler(this.inscreverAulaHandler);
		request.setAttribute("model", model);
		model.setNumeroUtente(request.getParameter("numeroUtente"));
	
			
		if (validInput(model)) {
			try {
				double preco = this.inscreverAulaHandler.inscricaoAula(model.getAula(), intValue(model.getNumeroUtente()), model.getInscricao());
				model.clearFields();
				model.addMessage("Custo total da inscrição: "+preco+"€");
			} 
			catch (ApplicationException e) {
				model.addMessage("Erro ao inscrever na aula: " + e.getMessage());
			}
		} else {
			model.addMessage("Erro na validação dos dados da inscrição");
		}
		request.getRequestDispatcher("/inscricaoAula/novaInscricao.jsp").forward(request, response);
	}

	
	private boolean validInput(InscricaoModel model) {
		return isFilled(model, model.getNumeroUtente(), "Tem de indicar o numero de utente")
			&& isInt(model, model.getNumeroUtente(), "Numero de utente tem caracteres inválidos");
	}

}
