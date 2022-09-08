package DemandasDm.api.rest.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("deprecation")
@Entity
public class Telefone {
	
	@Id
	@GeneratedValue(generator = "tell_id", strategy = GenerationType.AUTO)
	private Long id;
	
	private String numero;
	
	
	@JsonIgnore
	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@ForeignKey(name = "usuario_id")
	private Usuario user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}
	
	
}
