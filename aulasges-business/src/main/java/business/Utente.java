package business;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 * Entity implementation class for Entity: Utente
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Utente.FIND_BY_ID, query="SELECT u FROM Utente u WHERE u.numeroUtente = :" + Utente.UTENTE_ID),
})
public class Utente {

	public static final String FIND_BY_ID = "Utente.findById";

	public static final String UTENTE_ID = "numeroUtente";

	
	@Column(nullable = false)
	private String nome;
	
	@Id @GeneratedValue
	private int numeroUtente;
	
	@Column(nullable = false)
	private int nif;

	public Utente() {
	}
	
	/**
	 * Cria um utente dado um nome, numero de utente e nif
	 * 
	 * @param nome nome do utente
	 * @param numeroUtente numero do utente
	 * @param nif nif
	 */
	public Utente(String nome, int numeroUtente, int nif) {
		this.nome=nome;
		this.numeroUtente = numeroUtente;
		this.nif=nif;
	}
	
	/**
	 * Obtem numero de utente
	 * 
	 * @return numero
	 */
	public int getNumeroUtente() {
		return this.numeroUtente;
	}
	
	/**
	 * Obtem nif
	 * 
	 * @return nif
	 */
	public int getNif() {
		return nif;
	}
	
	/**
	 * Obtem nome do utente
	 * 
	 * @return nome 
	 */
	public String getNome() {
		return nome;
	}
   
}
