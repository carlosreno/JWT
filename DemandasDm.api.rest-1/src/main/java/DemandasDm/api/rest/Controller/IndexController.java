package DemandasDm.api.rest.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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




@CrossOrigin	
@RestController
@RequestMapping(value = "/usuario")
public class IndexController {
	
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
	public ResponseEntity<Usuario> Cadastrar(@RequestBody Usuario user){
		
		
		for (int i = 0; i < user.getListTell().size(); i++) {
			user.getListTell().get(i).setUser(user);
		}
		
		Usuario userSalvo = userRep.save(user);
		return new ResponseEntity<Usuario>(userSalvo, HttpStatus.OK);
	}
	
	@PutMapping(value = "/atualizar", produces = "application/json")
	public ResponseEntity<Usuario> Atualizar(@RequestBody Usuario user){
		
		for (int pos = 0; pos < user.getListTell().size(); pos++) {
			user.getListTell().get(pos).setUser(user);
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
