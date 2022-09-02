package DemandasDm.api.rest.Respository;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import DemandasDm.api.rest.Model.Usuario;

@Configuration
@ComponentScan
@Component
@Scope("prototype")
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	
	@Query("select u from Usuario where u.login=?1")
	Usuario findUserByLogin(String login);
	
	
}
