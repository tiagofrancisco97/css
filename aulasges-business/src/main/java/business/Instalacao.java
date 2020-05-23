package business;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import facade.exceptions.ApplicationException;

/**
 * Entity implementation class for Entity: Instalacao
 *
 */
@Entity

@NamedQueries({ 
	@NamedQuery(name = Instalacao.FIND_BY_NAME, query = "SELECT c FROM Instalacao c WHERE c.nome = :"
			+ Instalacao.INSTALACAO_NAME), 
	@NamedQuery(name = Instalacao.GET_ALL_NAMES, query = "SELECT s.nome FROM Instalacao s") 
})
public class Instalacao {

	public static final String FIND_BY_NAME = "Instalacao.findByName";
	public static final String INSTALACAO_NAME = "nome";

	public static final String GET_ALL_NAMES = "Instalacao.getAllNames";

	/**
	 * Space primary key. Needed by JPA.
	 */
	@Id @GeneratedValue
	private int id;
	
	@Version
	private int version;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoInstalacao tipo;

	@Column(nullable = false, unique = true)
	private String nome;

	@Column(nullable = false)
	private int lotacaoMax;

	@OneToMany(mappedBy = "instalacao", cascade = { PERSIST, MERGE, REMOVE })
	private List <AulaAtiva> aulasAtivas;

	@ManyToMany(cascade = { PERSIST, MERGE, REMOVE })
	private List<Modalidade> modalidades;

	public Instalacao() {
	}

	/**
	 * Cria uma instala��o 
	 * 
	 * @param nome nome da instala��o
	 * @param tipo tipo da instala��o
	 * @param lotacaoMax lota��o maxima da instala��o
	 */
	public Instalacao(String nome, TipoInstalacao tipo, int lotacaoMax) {
		this.tipo = tipo;
		this.nome = nome;
		this.lotacaoMax = lotacaoMax;
		this.aulasAtivas= new ArrayList<>();
		this.modalidades= new ArrayList<>();
	}

	/**
	 * Obtem tipo da instala��o
	 * @return tipo da instala��o
	 */
	public TipoInstalacao getTipo() {
		return tipo;
	}

	/**
	 * Obtem nome da instala��o
	 * @return nome da instala��o
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Obtem lota��o maxima da instala��o
	 * @return lota��o maxima da instala��o
	 */
	public int getLotacaoMax() {
		return lotacaoMax;
	}

	/**
	 * Obtem modalidades permitidas nesta instala��o
	 * @return modalidades permitidas nesta instala��o
	 */
	public List<Modalidade> getModalidades() {
		return modalidades;
	}

	public void addModalidades(Modalidade modalidade) {
		this.modalidades.add(modalidade);
	}

	public int getId() {
		return id;
	}


	public List<AulaAtiva> getAulasAtivas() {
		return aulasAtivas;
	}


	/**
	 * Verifica se a instala��o est� livre para um dado periodo, hora e dias 
	 * da semana de uma aula 
	 * 
	 * @param diasDaSemana Dias da semana em que a aula vai ocorrer
	 * @param horaDeInicio Hora de inicio da aula
	 * @param duracao dura��o da aula
	 * @param inicio Periodo inicial
	 * @param fim Periodo final
	 * @return true se a instala��o esta livre, false cc
	 */
	public boolean estaDisponivel(List<Dia> diasDaSemana, Calendar horaDeInicio, int duracao, Calendar inicio, Calendar fim) {
		for (AulaAtiva aula : this.aulasAtivas) {
			if(!inicio.after(aula.getFim()) && diasDaSemana.containsAll(aula.getAula().getDiasDaSemana())) {
				Calendar horaAulaAtiva =aula.getAula().getHoraDeInicio();
				int hia = (horaAulaAtiva.get(Calendar.HOUR_OF_DAY) *60)+ horaAulaAtiva.get(Calendar.MINUTE);
				int horaInicial = (horaDeInicio.get(Calendar.HOUR_OF_DAY) *60) +horaDeInicio.get(Calendar.MINUTE);//aula a ativar
				int horaFinal = horaInicial+duracao;
				if(!(horaFinal<=hia || horaInicial>= hia+aula.getAula().getDuracao())) {
					return false;
				}
			}
		}
		return true;
	}

	

	/**
	 * Adiciona uma aula nesta instala��o
	 * @param aula aula a adicionar 
	 */
	public void adicionaAula(AulaAtiva aula) {
		this.aulasAtivas.add(aula);
	}

	/**
	 * Visualiza a ocupa��o desta instala��o
	 * @return lista de entradas com as aulas a decorrer nesta instala��o
	 * @throws ApplicationException
	 */
	public List<AulaAtiva> visualizaOcupacao() throws ApplicationException {
		if(this.aulasAtivas.isEmpty()) {
			throw new ApplicationException("Ainda n�o existem aulas nesta Instala��o!");
		}
		return this.aulasAtivas;
	}

	/**
	 * Verifica se nesta instala��o pode ser praticada uma modalidade
	 * @param modalidade modalidade a verificar
	 * @return true se tiver, false cc
	 */
	public boolean contemModalidade(String modalidade) {
		for (Modalidade m : this.modalidades) {
			if(m.getNome().equals(modalidade)) {
				return true;
			}
		}
		return false;
	}

}
