package facade.dto;

import java.util.Calendar;
import java.util.List;

import business.Dia;

public class Aula {
	
	private String nome;
	private List<Dia> diasDaSemana;
	private Calendar horaDeInicio;
	private String instalacao;
	private int vagas;
	
	private Calendar inicio;
	private Calendar fim;
	private Calendar hora;
	/**
	 * Cria uma representaçao da aula para os utilizadores
	 * 
	 * @param nome nome da aula
	 * @param diasDaSemana dias da semana
	 * @param horaDeInicio hora de inicio da aula
	 * @param instalacao nome da instalação
	 * @param vagas numero de vagas na aula
	 */
	public Aula(String nome, List<Dia> diasDaSemana, Calendar horaDeInicio, String instalacao, int vagas) {
		this.nome = nome;
		this.diasDaSemana = diasDaSemana;
		this.horaDeInicio = horaDeInicio;
		this.instalacao = instalacao;
		this.vagas = vagas;
	}
	
	/**
	 * Cria uma representaçao da aula para os utilizadores
	 * 
	 * @param nome nome da aula
	 * @param diasDaSemana dias da semana
	 * @param horaDeInicio hora de inicio da aula
	 * @param instalacao nome da instalação
	 * @param vagas numero de vagas na aula
	 */
	public Aula(String nome, Calendar inicio, Calendar fim, Calendar hora) {
		this.nome = nome;
		this.inicio=inicio;
		this.fim=fim;
		this.hora=hora;
	}
	
	/**
	 * Obtem nome da aula
	 * 
	 * @return nome
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Obtem dias da semana em que a aula ocorre
	 * 
	 * @return dias da semana
	 */
	public List<Dia> getDiasDaSemana() {
		return diasDaSemana;
	}
	
	/**
	 * Hora de inicio da aula
	 * 
	 * @return Hora de inicio
	 */
	public Calendar getHoraDeInicio() {
		return horaDeInicio;
	}
	
	/**
	 * nome da instalação
	 * 
	 * @return nome
	 */
	public String getInstalacao() {
		return instalacao;
	}
	
	/**
	 * Vagas na aula
	 * 
	 * @return vagas
	 */
	public int getLotacao() {
		return vagas;
	}


	public Calendar getInicio() {
		return inicio;
	}

	public Calendar getFim() {
		return fim;
	}

	public Calendar getHora() {
		return hora;
	}

	/**
	 * Representação textual da aula
	 * 
	 * @return aula 
	 */
	public String toString() {
		return "Aula " + nome + ", ocorre nos dias " + diasDaSemana + "\n"
				+ ", começa às " + horaDeInicio.get(Calendar.HOUR_OF_DAY)+":"+horaDeInicio.get(Calendar.MINUTE)
				+ ", na instalacao " + instalacao + ". \n Ainda tem " + vagas + " vagas";
	}
	
	public String toString2() {
		int inicialM=this.inicio.get(Calendar.MONTH)+1;
		int endMonth =this.fim.get(Calendar.MONTH)+1;
		return "Nome da aula: "+this.nome+"\n"
				+ "A aula ocorre de "+this.inicio.get(Calendar.DAY_OF_MONTH)+"/"+inicialM+"/"
				+this.inicio.get(Calendar.YEAR)+" até "+this.fim.get(Calendar.DAY_OF_MONTH)+"/"
				+endMonth+"/"+this.fim.get(Calendar.YEAR)+"\n"
				+ "A aula começa às "+this.hora.get(Calendar.HOUR_OF_DAY)+":"+this.hora.get(Calendar.MINUTE)+"H";
	}
	
	
	

}
