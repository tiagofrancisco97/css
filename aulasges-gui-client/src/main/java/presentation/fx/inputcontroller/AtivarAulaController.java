package presentation.fx.inputcontroller;

import java.util.Calendar;
import java.util.function.UnaryOperator;

import facade.exceptions.ApplicationException;
import facade.handlers.IAulaServiceRemote;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.AtivarAulaModel;

public class AtivarAulaController extends BaseController {

	@FXML
	private TextField designacaoAulaTextField;

	@FXML
	private TextField dataInicialTextField;

	@FXML
	private TextField dataFinalTextField;
	
	@FXML
	private TextField numAlunosTextField;

	@FXML
	private ComboBox<String> instalacoesComboBox;

	private AtivarAulaModel model;

	private IAulaServiceRemote aulaService;
	

	public void setCustomerService(IAulaServiceRemote aulaService) {
		this.aulaService = aulaService;
	}

	public void setModel(AtivarAulaModel model) {
		this.model = model;
		designacaoAulaTextField.textProperty().bindBidirectional(model.designacaoAulaProperty());
		dataInicialTextField.textProperty().bindBidirectional(model.dataInicialProperty(), new NumberStringConverter());
		dataFinalTextField.textProperty().bindBidirectional(model.dataFinalProperty(), new NumberStringConverter());
		numAlunosTextField.textProperty().bindBidirectional(model.numAlunosProperty(), new NumberStringConverter());   	
		instalacoesComboBox.setItems(model.getInstalacoes());   
		instalacoesComboBox.setValue(model.getSelectedInstalacao());
	}

	@FXML
	private void initialize() {
		UnaryOperator<Change> integerFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("[0-9]*")) { 
				return change;
			}
			return null;
		};

		numAlunosTextField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),
				0, integerFilter));
		/*phoneNumberTextField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),
				0, integerFilter));*/
	}

	@FXML
	void ativarAulaAction(ActionEvent event) {
		String errorMessages = validateInput();

		if (errorMessages.length() == 0) {
			try {
				if(model.getDataInicial().contains("-") && model.getDataFinal().contains("-")) {
					Calendar i = Calendar.getInstance();
					i.set(Calendar.DAY_OF_MONTH, Integer.parseInt(model.getDataInicial().split("-")[0]));
					i.set(Calendar.MONTH, Integer.parseInt(model.getDataInicial().split("-")[1])-1);
					i.set(Calendar.YEAR, Integer.parseInt(model.getDataInicial().split("-")[2]));
					
					Calendar f = Calendar.getInstance();
					f.set(Calendar.DAY_OF_MONTH, Integer.parseInt(model.getDataFinal().split("-")[0]));
					f.set(Calendar.MONTH, Integer.parseInt(model.getDataFinal().split("-")[1])-1);
					f.set(Calendar.YEAR, Integer.parseInt(model.getDataFinal().split("-")[2]));
					aulaService.ativaAula(model.getDesignacaoAula(), model.getSelectedInstalacao(), i, f, model.getNumAlunos());
					
					model.clearProperties();
					showInfo(i18nBundle.getString("new.customer.success"));
				}
			} catch (ApplicationException e) {
				showError(i18nBundle.getString("new.customer.error.adding") + ": " + e.getMessage());
			}
		} else
			showError(i18nBundle.getString("new.customer.error.validating") + ":\n" + errorMessages);
			
	}

	private String validateInput() {	
		StringBuilder sb = new StringBuilder();
		String aula = model.getDesignacaoAula();
		if (aula == null || aula.length() == 0)
			sb.append(i18nBundle.getString("new.customer.invalid.designation"));
		if (model.getNumAlunos() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.customer.invalid.VATNumber"));
		}
		if (model.getSelectedInstalacao() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.customer.invalid.discount"));
		}
		//verificar datas
		/*if (model.getSelectedInstalacao() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.customer.invalid.discount"));
		}*/

		return sb.toString();
	}

	@FXML
	void instalacaoTypeSelected(ActionEvent event) {
		model.setSelectedInstalacao(instalacoesComboBox.getValue());
	}

}
