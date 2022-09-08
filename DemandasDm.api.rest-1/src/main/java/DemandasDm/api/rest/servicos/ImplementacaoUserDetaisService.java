package DemandasDm.api.rest.servicos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import DemandasDm.api.rest.Model.Usuario;
import DemandasDm.api.rest.Respository.UsuarioRepository;




@Service
public class ImplementacaoUserDetaisService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = usuarioRepository.findUserByLogin(username);
		
		if (user==null) {
			throw new UsernameNotFoundException("Usuario não encontrado");
		}
		
		return new User(user.getLogin(), user.getSenha(), user.getAuthorities());
	}

}
