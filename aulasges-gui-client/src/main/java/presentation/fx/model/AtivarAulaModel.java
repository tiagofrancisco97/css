package presentation.fx.model;


import java.util.Calendar;

import business.Instalacao;
import facade.exceptions.ApplicationException;
import facade.handlers.IAulaServiceRemote;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class AtivarAulaModel {

	private final StringProperty designacaoAula;
	private final ObjectProperty<String> selectedInstalcao;
	private final ObjectProperty<String> dataInicial;
	private final ObjectProperty<String> dataFinal;
	private final IntegerProperty numAlunos;
	
	private final ObservableList<String> instalacoes;
	
	public AtivarAulaModel(IAulaServiceRemote as) {
		designacaoAula = new SimpleStringProperty();
		numAlunos = new SimpleIntegerProperty();
		this.instalacoes = FXCollections.observableArrayList();
		try {
			as.iniciarAtivacaoAula().forEach(i -> instalacoes.add(i));
		} catch (ApplicationException e) {
			// no discounts added
		}
		selectedInstalcao = new SimpleObjectProperty<>(null);
		dataInicial = new SimpleObjectProperty<>(null);
		dataFinal = new SimpleObjectProperty<>(null);
	}

	public ObjectProperty<String> selectedInstalacaoProperty() {
		return selectedInstalcao;
	}

	public final String getSelectedInstalacao() {
		return selectedInstalcao.get();
	}

	public final void setSelectedInstalacao(String i) {
		selectedInstalcao.set(i);
	}

	public ObservableList<String> getInstalacoes() {
		return instalacoes;
	}
	
	
	public String getDesignacaoAula() {
		return designacaoAula.get();
	}

	public StringProperty designacaoAulaProperty() {
		return designacaoAula;
	}

	public int getNumAlunos() {
		return numAlunos.get();
	}
	
	public IntegerProperty numAlunosProperty() {
		return numAlunos;
	}

	public String getDataInicial() {
		return dataInicial.get();
	}
	
	public ObjectProperty dataInicialProperty() {
		return dataInicial;
	}	
	
	public String getDataFinal() {
		return dataFinal.get();
	}
	
	public ObjectProperty dataFinalProperty() {
		return dataFinal;
	}

	public void clearProperties() {
		designacaoAula.set("");
		numAlunos.set(0);
		selectedInstalcao.set(null);
		dataInicial.set(null);
		dataFinal.set(null);
	}
	
}


