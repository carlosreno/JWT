package DemandasDm.api.rest.servicos;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import DemandasDm.api.rest.Model.Usuario;
import DemandasDm.api.rest.Respository.UsuarioRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JWTTokenAutenticacaoService {	
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	private static final long EXPIRATION_TIME = 172800000;
	
	private static final String KEY = "AsenhaEsenha";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HED_STRING = "Authorization";
	
	/*Gerando Token*/
	public void addAutentication(HttpServletResponse response,String username) throws IOException{
		String JWT = Jwts.builder() /*Chama o gerador de token*/
				.setSubject(username) /*Adiciona o user*/
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /*Tempo do token*/
				.signWith(SignatureAlgorithm.HS512, KEY).compact(); /*Compacta as informacoes no token*/
		
		/*Junta o token com o a pre-senha*/
		String Token = TOKEN_PREFIX +" " + JWT;
		
		response.addHeader(HED_STRING, Token);
		
		/*Resposta no corpo*/
		
		response.getWriter().write("{\"Authorization\": \""+Token+"\"}");
		
	}
	
	public Authentication getAuthentication(HttpServletRequest request) {
		
		String Token = request.getHeader(HED_STRING);
		
		if (Token !=null) {
			String user = Jwts.parser().setSigningKey(KEY)
					.parseClaimsJws(Token.replace(TOKEN_PREFIX, ""))
					.getBody().getSubject();
			if (user != null) {
				Usuario usuario = usuarioRepository.findUserByLogin(user);
				if (usuario != null) {
					return new UsernamePasswordAuthenticationToken(
							usuario.getLogin(),
							usuario.getSenha(),
							usuario.getAuthorities());
				}
		}
		}
		return null;
	}
	
}
