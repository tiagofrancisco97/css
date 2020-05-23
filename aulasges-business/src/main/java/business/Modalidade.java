package business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Entity implementation class for Entity: Modalidade
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Modalidade.GET_ALL_NAMES, query="SELECT m.nome FROM Modalidade m"),
	@NamedQuery(name=Modalidade.FIND_BY_NAME, query="SELECT m FROM Modalidade m WHERE m.nome = :" + Modalidade.MODALIDADE_NAME),
})
public class Modalidade {

	// Named query name constants
	public static final String GET_ALL_NAMES = "Modalidade.getAllNames";

	public static final String FIND_BY_NAME = "Modalidade.findByName";

	public static final String MODALIDADE_NAME = "nome";

	/**
	 * Space primary key. Needed by JPA.
	 */
	@Id @GeneratedValue
	private int id;
	
	@Column(nullable = false, unique = true)
	private String nome;
	
	@Column(nullable = false)
	private int duracaoMinima;
	
	@Column(nullable = false)
	private double custoPorHora;

	public Modalidade() {
	}
   
	/**
	 * Cria uma modalidade
	 * 
	 * @param nome nome da modalidade
	 * @param duracao dura��o minima da modalidade
	 * @param custo custo por hora da modalidade
	 */
	public Modalidade(String nome, int duracao, double custo) {
		this.nome = nome;
		this.duracaoMinima=duracao;
		this.custoPorHora=custo;
	}
	
	/**
	 * Obtem dura��o minima de uma aula desta modalidade
	 * @return dura��o minima
	 */
	public int getDuracaoMinima() {
		return duracaoMinima;
	}
	
	
	/**
	 * Obtem o custo por hora associado a esta modalidade
	 * @return custo por hora
	 */
	public double getCustoPorHora() {
		return custoPorHora;
	}


	/**
	 * Obtem nome da modalidade
	 * @return nome
	 */
	public String getNome() {
		return this.nome;
	}

	public int getId() {
		return id;
	}
	
}
