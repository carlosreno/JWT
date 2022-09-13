package DemandasDm.api.rest.Controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.Buffer;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DemandasDm.api.rest.Model.Usuario;
import DemandasDm.api.rest.Respository.UsuarioRepository;
import DemandasDm.api.rest.servicos.ServicoEmail;



//

@CrossOrigin	
@RestController
@RequestMapping(value = "/usuario")
public class IndexController {
	
	@Autowired
	private ServicoEmail servicoEmail;
	
	@Autowired
	private UsuarioRepository userRep;
	
	
	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<List<Usuario>> ListarTD(){
		
		List<Usuario> ListaUsers = (List<Usuario>) userRep.findAll();
		
		
		return new ResponseEntity<List<Usuario>>(ListaUsers, HttpStatus.OK);
		
	}
	
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Usuario> UsuarioID(@PathVariable (value = "id") Long id){
		
		
		
		Optional<Usuario> user = userRep.findById(id);
		
		return new ResponseEntity<Usuario>(user.get(),HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/cadastrar", produces = "application/json")
	public ResponseEntity<Usuario> Cadastrar(@RequestBody Usuario user) throws Exception{
		
		user.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));
		
		for (int i = 0; i < user.getListTell().size(); i++) {
			user.getListTell().get(i).setUser(user);
		}
		
		URL url = new URL("https://viacep.com.br/ws/"+user.getCep()+"/json/");
		URLConnection connection =  url.openConnection();
		InputStream is =  connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String cep =  "";
		StringBuilder jsonCep =  new StringBuilder();
		
		while ((cep = br.readLine()) != null) {
			jsonCep.append(cep);
		}
		System.out.println(jsonCep.toString());
		
		
		Usuario userSalvo = userRep.save(user);
		
		
		
		return new ResponseEntity<Usuario>(userSalvo, HttpStatus.OK);
	}
	
	@PutMapping(value = "/atualizar", produces = "application/json")
	public ResponseEntity<Usuario> Atualizar(@RequestBody Usuario user){
		
		for (int pos = 0; pos < user.getListTell().size(); pos++) {
			user.getListTell().get(pos).setUser(user);
		}
		
		Usuario userTemp = userRep.findUserByLogin(user.getLogin());
		
		if (!user.getSenha().equals(userTemp.getSenha())) {
			user.setSenha(new BCryptPasswordEncoder().encode(user.getSenha()));
			
		}
		
		Usuario userUp = userRep.save(user);
		
		
		return new ResponseEntity<Usuario>(userUp, HttpStatus.OK);
		
	}
	
	@DeleteMapping(value = "/{id}", produces = "application/text")
	public ResponseEntity<Usuario> deletID(@PathVariable (value = "id") Long id){
		userRep.deleteById(id);
		
		return new ResponseEntity<Usuario>(HttpStatus.OK);
		
	}
	
	
}
