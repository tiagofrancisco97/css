package presentation.fx.model;


import facade.exceptions.ApplicationException;
import facade.handlers.IAulaServiceRemote;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.scene.control.CheckBox;

public class CriarAulaModel {

	private final StringProperty modalidade;
	private final StringProperty nome;
	private final IntegerProperty duracao;
	private final StringProperty horaInicio;
	private final BooleanProperty init;
	private ObservableSet<CheckBox> selectedCheckBoxes;
	private ObservableSet<CheckBox> unselectedCheckBoxes;


	public CriarAulaModel(){
		modalidade = new SimpleStringProperty();
		nome = new SimpleStringProperty();
		duracao = new SimpleIntegerProperty();
		horaInicio = new SimpleStringProperty();
		selectedCheckBoxes = FXCollections.observableSet();
		unselectedCheckBoxes = FXCollections.observableSet();
		init = new SimpleBooleanProperty(false);
	}


	public StringProperty modalidadeProperty() {
		return modalidade;
	}

	public String getModalidade() {
		return modalidade.get();
	}


	public StringProperty nomeProperty() {
		return nome;
	}

	public String getNome() {
		return nome.get();
	}


	public IntegerProperty duracaoProperty() {
		return duracao;
	}

	public int getDuracao() {
		return duracao.get();
	}


	public StringProperty horaInicioProperty() {
		return horaInicio;
	}

	public String getHoraInicio() {
		return horaInicio.get();
	}


	public BooleanProperty getInit() {
		return init;
	}


	public ObservableSet<CheckBox> getSelectedCheckBoxes() {
		return selectedCheckBoxes;
	}


	public ObservableSet<CheckBox> getUnselectedCheckBoxes() {
		return unselectedCheckBoxes;
	}


	public void clearProperties() {
		nome.set("");
		modalidade.set("");
		duracao.set(0);
		horaInicio.set("");
		selectedCheckBoxes = null;
		unselectedCheckBoxes = null;
	}

	public void configureCheckBox(CheckBox checkBox) {

		if (checkBox.isSelected()) {
			selectedCheckBoxes.add(checkBox);
		} else {
			unselectedCheckBoxes.add(checkBox);
		}

		checkBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
			if (isNowSelected) {
				unselectedCheckBoxes.remove(checkBox);
				selectedCheckBoxes.add(checkBox);
			} else {
				selectedCheckBoxes.remove(checkBox);
				unselectedCheckBoxes.add(checkBox);
			}

		});

	}

	}



