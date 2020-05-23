package business;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.SEQUENCE;
import static javax.persistence.TemporalType.DATE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

import business.utils.DateUtils;
import javax.persistence.OneToOne;



/**
 * Entity implementation class for Entity: AulaAtiva
 *
 */
@Entity


@NamedQueries({ 
	@NamedQuery(name = AulaAtiva.FIND_BY_INSTALACAO, query = "SELECT c FROM AulaAtiva c"
		+ " WHERE :"+AulaAtiva.GIVEN_DATE+" BETWEEN c.inicio "
				+" AND c.fim and c.instalacao = :" + AulaAtiva.INSTALACAO_ID+" and :"
		+AulaAtiva.GIVEN_DAY+" member of c.aula.diasDaSemana"), 
	@NamedQuery(name = AulaAtiva.AA_FIND_BY_NAME, query = "SELECT c FROM AulaAtiva c WHERE c.aula.nome = :"
			+ AulaAtiva.AULAATIVA_NAME),
	@NamedQuery(name = AulaAtiva.FIND_BY_MODALIDADE, query = "SELECT a FROM AulaAtiva a"
			+ " WHERE a.aula.modalidade = :" + AulaAtiva.MODALIDADE_ID +  
			  " ORDER BY a.aula.horaDeInicio"), 
})
public class AulaAtiva {
	
	public static final String FIND_BY_INSTALACAO = "AulaAtiva.findByInstalacao";
	public static final String GIVEN_DATE = "date";
	public static final String GIVEN_DAY = "day";
	public static final String INSTALACAO_ID = "instalacao";
	public static final String MODALIDADE_ID = "modalidade";
	public static final String AA_FIND_BY_NAME = "AulaAtiva.findByName";
	public static final String AULAATIVA_NAME = "nome";
	public static final String FIND_BY_MODALIDADE = "AulaAtiva.findByModalidade";
	
	
	/**
	 * Space primary key. Needed by JPA.
	 */
	@Id @GeneratedValue(strategy = SEQUENCE)
	private int id;
	
	@ManyToOne
	private Instalacao instalacao;
	


	@OneToMany(cascade = { PERSIST, MERGE, REMOVE })
	@JoinColumn(name = "AulaAtiva_id")
	private List<Sessao> sessoes;
	
	
	
	@OneToOne
	private Aula aula;
	
	
	@Column(nullable = false)
	private int lotacao;
	
	@Temporal(DATE)
	@Column(nullable = false)
	private Calendar inicio;
	
	@Temporal(DATE)
	@Column(nullable = false)
	private Calendar fim;
	
	public AulaAtiva() {
		
	}
	
	/**
	 * Cria uma aula ativa 
	 * 
	 * @param Aula aula
	 * @param lotacao lotacao da aula 
	 * @param instalacao instalacao onde ocorre a aula
	 * @param inicio data de inicio
	 * @param fim data de fim
	 */
	public AulaAtiva(Aula aula, int lotacao, Instalacao instalacao, Calendar inicio, Calendar fim) {
		
		this.aula=aula;
		this.instalacao=instalacao;
		this.inicio=inicio;
		this.fim=fim;
		this.lotacao=lotacao;
		this.sessoes=new ArrayList<>();
		criaSessoes();
	}
	
	/**
	 * Obtem sessoes da aula
	 * 
	 * @return Sessoes da aula
	 */
	public List<Sessao> getSessoes() {
		return this.sessoes;
	}
	
	public Aula getAula() {
		return aula;
	}
	
	/**
	 * Obtem numero de utentes da aula 
	 * 
	 * @return numero de utentes
	 */
	public int getLotacao() {
		return this.lotacao;
	}
	
	/**
	 * Obtem nome da instala��o onde ocorre a aula
	 * 
	 * @return nome
	 */
	
	public String getInstalacao() {
		return this.instalacao.getNome();
	}
	
	/**
	 * Obtem data de inicio da aula
	 * 
	 * @return data inicio
	 */
	public Calendar getInicio() {
		return inicio;
	}
	
	/**
	 * Obtem data de fim da aula
	 * 
	 * @return Data de fim
	 */
	public Calendar getFim() {
		return fim;
	}
	

	/**
	 * Ativa uma aula e cria as sess�es respetivas
	 */
	public void criaSessoes() {
		Calendar i = (Calendar) this.inicio.clone();
		Calendar f = (Calendar) this.fim.clone();
		for (Dia dia : this.aula.getDiasDaSemana()) {
			contaDias(dia, i, f) ;
		}
	}
	
	/**
	 * Conta o numero de um dia da semana num periodo de tempo
	 * @param dia dia da semana
	 * @param inicio data inicial de uma aula
	 * @param fim data final de uma aula
	 * @param em 
	 * @return numero de dias que um dado dia da semana ocorre num periodo
	 */
	public void contaDias(Dia dia, Calendar inicio, Calendar fim) {
		int d =0;
		if(dia == Dia.SEGUNDA) {
			d= Calendar.MONDAY;
		}
		if(dia == Dia.TERCA) {
			d= Calendar.TUESDAY;
		}
		if(dia == Dia.QUARTA) {
			d= Calendar.WEDNESDAY;
		}
		if(dia == Dia.QUINTA) {
			d= Calendar.THURSDAY;
		}
		if(dia == Dia.SEXTA) {
			d= Calendar.FRIDAY;
		}
		if(dia == Dia.SABADO) {
			d= Calendar.SATURDAY;
		}
		if(dia == Dia.DOMINGO) {
			d= Calendar.SUNDAY;
		}
		Calendar a = (Calendar) inicio.clone();
		Calendar b = (Calendar) fim.clone();

		while (a.before(b)) {
			if (a.get(Calendar.DAY_OF_WEEK) == d) {
				a.set(Calendar.HOUR_OF_DAY, this.aula.getHoraDeInicio().get(Calendar.HOUR_OF_DAY));
				a.set(Calendar.MINUTE, this.aula.getHoraDeInicio().get(Calendar.MINUTE));
				Sessao s =new Sessao(dia,this.lotacao, (Calendar) a.clone());
				this.sessoes.add(s);
				a.add(Calendar.DATE, 7);
			} else {
				a.add(Calendar.DATE, 1);
			}
		}

	}

	/**
	 * Verifica se tem uma sessao nas proximas 24 horas e se ha vaga
	 * @param inscricao 
	 * 
	 * @return Se tem sessao
	 */
	public boolean verificaSessao(String inscricao) {
		return numeroVagas(inscricao)>=1;
	}
	
	/**
	 * Numero de vagas existentes na aula
	 * @param inscricao 
	 * 
	 * @return Numero de vagas da proxima sessao no caso de ser inscricao avulso, 
	 *         na inscricao regular � o numero minimo de vagas de todas as sessoes
	 */
	public int numeroVagas(String inscricao) {
		int vagas = 0;
		if(inscricao.equals("avulso")) {
			Calendar atual = DateUtils.getAtual();
			Calendar depois = DateUtils.getAtual();
			depois.add(Calendar.DAY_OF_MONTH, 1);
			for (Sessao s : this.sessoes) {
				Calendar horaInicio = (Calendar) s.getHoraInicio().clone();
				if(atual.compareTo(horaInicio) * horaInicio.compareTo(depois) > 0) {
					return s.getVagas();
				}
			}
			return vagas;
		}
		Calendar atual = DateUtils.getAtual();
		List<Integer> num = new ArrayList<>();
		for (Sessao s : this.sessoes) {
			if(atual.before(s.getHoraInicio())) {
				num.add(s.getVagas());
			}
		}
		return Collections.max(num);
	}

	/**
	 * Inscreve o utente nas sessoes da aula
	 * 
	 * @param inscricao tipo de inscricao
	 * @param utente numero do utente
	 * @return 
	 */
	public Inscricao inscreveUtenteSessoes(String inscricao, Utente utente) {
		if(inscricao.equals("avulso")) {
			Calendar atual = DateUtils.getAtual();
			Calendar depois = DateUtils.getAtual();
			depois.add(Calendar.DAY_OF_MONTH, 1);
			for (Sessao s : this.sessoes) {
				if(atual.compareTo(s.getHoraInicio()) * s.getHoraInicio().compareTo(depois) > 0) {
					s.adicionaUtente();
					return new Avulso(utente, s);
				}
			}
		} else {
			Calendar atual = DateUtils.getAtual();
			List <Sessao> sessoesAInscrever= new ArrayList<>();
			for (Sessao s : this.sessoes) {
				if(atual.before(s.getHoraInicio())) {
					s.adicionaUtente();
					sessoesAInscrever.add(s);
				}
			}
			return new Regular(utente, sessoesAInscrever);
		}
		return null;
	}
	
	

	/**
	 * Obtem data da ultima sessao de uma aula
	 * 
	 * @return Data
	 */
	public Calendar dataUltimaSessao() {
		return this.sessoes.get(this.sessoes.size()-1).getHoraInicio();
	}
   
}
