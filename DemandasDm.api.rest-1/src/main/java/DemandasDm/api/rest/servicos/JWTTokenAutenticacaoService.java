package DemandasDm.api.rest.servicos;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.config.annotation.rsocket.RSocketSecurity.JwtSpec;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import DemandasDm.api.rest.Respository.UsuarioRepository;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Component
public class JWTTokenAutenticacaoService {	
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private static final long EXPIRATION_TIME = 172800000;
	
	private static final String SECRET = "AsenhaEsenha";
	
	private static final String TOKEN_PREFIX = "Bearer";
	
	private static final String HED_STRING = "Authorization";
	
	/*Gerando Token*/
	public void addAutentication(HttpServletResponse response,String username) throws Exception{
		String JWT = Jwts.builder() /*Chama o gerador de token*/
				.setSubject(username) /*Adiciona o user*/
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) /*Tempo do token*/
				.signWith(SignatureAlgorithm.RS512, SECRET).compact(); /*Compacta as informacoes no token*/
		
		/*Junta o token com o a pre-senha*/
		String Token = TOKEN_PREFIX +"" + JWT;
		
		response.addHeader(HED_STRING, Token);
		
		/*Resposta no corpo*/
		
		response.getWriter().write("{\"Authorization\": \""+Token+"\"}");
		
	}
	public AuthenticateAction getAuthenticateAction(HttpServletRequest request) {
		
		String Token = request.getHeader(HED_STRING);
		
		if (Token !=null) {
			String user = Jwts.parser().setSigningKey(SECRET)
					.parseClaimsJws(Token.replace(TOKEN_PREFIX, ""))
					.getBody().getSubject();
			if (user != null) {
				user.u
				
			
			}
		}else {
			return null;
		}
		return null;
		
	}
	
}
