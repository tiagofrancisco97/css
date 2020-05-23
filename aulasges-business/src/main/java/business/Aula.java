package business;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.TIME;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;

import business.utils.DateUtils;
import static javax.persistence.TemporalType.DATE;


/**
 * Entity implementation class for Entity: Aula
 *
 */
@Entity

@NamedQuery(name = Aula.FIND_BY_NAME, query= "SELECT a FROM Aula a WHERE a.nome = :"+Aula.AULA_NAME)
public class Aula  {

	public static final String FIND_BY_NAME = "Aula.findByName";
	public static final String AULA_NAME = "nome";
	
	/**
	 * Space primary key. Needed by JPA.
	 */
	@Id @GeneratedValue(strategy = SEQUENCE)
	private int id;
	
	@Column(unique = true, nullable = false, length = 6)
	private String nome;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = EAGER)
	@CollectionTable(joinColumns = @JoinColumn(name = "Aula_id", referencedColumnName = "id"))
	@Column(nullable = false)
	private List<Dia> diasDaSemana;
	
	
	@Column(nullable = false)
	@Temporal(DATE)
	private Calendar horaDeInicio;
	
	@Column(nullable = false)
	private int duracao;
	
	
	@ManyToOne
	private Modalidade modalidade;


	public Aula() {
	}
	
	/**
	 * Cria uma nova aula dado o nome, dias da semana, hora de inicio, duracao e modalidade
	 * 
	 * @param nome nome da aula
	 * @param diasDaSemana dias da semana
	 * @param horaDeInicio hora de inicio da aula
	 * @param duracao duracao da aula
	 * @param modalidade modalidade da aula
	 * @pre verificaNome(nome)
	 * @pre isValidModalidade(modalidade)
	 * @pre verificaDuracao(duracao)
	 */
	public Aula(String nome, List<Dia> diasDaSemana, Calendar horaDeInicio, int duracao, Modalidade modalidade) {
		this.nome = nome;
		this.diasDaSemana = diasDaSemana;
		this.horaDeInicio = horaDeInicio;
		this.duracao = duracao;
		this.modalidade = modalidade;
	}


	/**
	 * Obtem modalidade da aula
	 * 
	 * @return Modalidade 
	 */
	public Modalidade getModalidade() {
		return modalidade;
	}
	
	
	/**
	 * Obtem nome da aula
	 * 
	 * @return nome
	 */
	public String getNome() {
		return this.nome;
	}
	
	/**
	 * Obtem dias da semana em que a aula ocorre
	 * 
	 * @return dias da semana
	 */
	public List<Dia> getDiasDaSemana() {
		return this.diasDaSemana;
	}
	
	/**
	 * Hora de inicio da aula
	 * 
	 * @return Hora de inicio
	 */
	public Calendar getHoraDeInicio() {
		Calendar hora = DateUtils.getAtual();
		hora.set(Calendar.HOUR_OF_DAY, this.horaDeInicio.get(Calendar.HOUR_OF_DAY));
		hora.set(Calendar.MINUTE, this.horaDeInicio.get(Calendar.MINUTE));
		return hora;
	}
	
	/**
	 * Obtem dura��o da aula
	 * 
	 * @return Dura��o
	 */
	public int getDuracao() {
		return this.duracao;
	}


	/**
	 * Obtem pre�o por hora da modalidade 
	 * 
	 * @return Pre�o por hora
	 */
	public double getPrecoHora() {
		return this.modalidade.getCustoPorHora();
	}
	
	public int getId() {
		return id;
	}


	/**
	 * Verifica se o nome � valido
	 * 
	 * @param nome nome da aula
	 * @return Se o nome � valido ou nao. 
	 */
	public static boolean verificaNome(String nome) {
		int alfanumericos = 0;
		for(int i =0; i <nome.length(); i++) {
			if(Character.isDigit(nome.charAt(i)) || Character.isLetter(nome.charAt(i))) {
				alfanumericos ++;
			}
		}
		return nome.length()==6 && alfanumericos >= 3 && !nome.contains(" ");
	}
   
}
