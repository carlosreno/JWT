package DemandasDm.api.rest.Respository;


import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import DemandasDm.api.rest.Model.Usuario;



@Primary
@Service
@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	
	
	
	@Query("select u from Usuario u where u.login =?1")
	Usuario findUserByLogin(String login);
	
		
}
