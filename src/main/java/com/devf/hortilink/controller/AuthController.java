package com.devf.hortilink.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devf.hortilink.dto.RegistroDTO;
import com.devf.hortilink.dto.UsuarioDTO;
import com.devf.hortilink.entity.Usuario;
import com.devf.hortilink.service.UsuarioService;
import com.devf.hortilink.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
    private JwtUtil jwtUtil;
	@Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha())
            );

            String token = jwtUtil.generateToken(request.getEmail());
            Usuario usuarioLogado = usuarioService.buscarPorEmail(request.getEmail());
            
            UsuarioDTO usuarioLogadoDto = UsuarioDTO.fromEntity(usuarioLogado);
           
            return ResponseEntity.ok(new AuthResponse(token, usuarioLogadoDto));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário ou senha inválidos");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistroDTO dto) {
        usuarioService.salvar(dto); // implemente para salvar com senha criptografada
        return ResponseEntity.ok("Usuário registrado com sucesso!");
    }
    
    @GetMapping("/verify")
    public ResponseEntity<Boolean> verificaEmail(@RequestParam String email) {
    	Boolean existe = usuarioService.existeComEmail(email);
    	return ResponseEntity.ok(existe);
    }
}

class AuthRequest {
    private String email;
    private String senha;
    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}
    public String getSenha(){return senha;}
    public void setSenha(String senha){this.senha = senha;}
}

class AuthResponse {
    private String token;
    private UsuarioDTO usuarioDto;
    public AuthResponse(String token, UsuarioDTO dto){this.token = token;this.usuarioDto = dto;}
    public String getToken(){return token;}
    public UsuarioDTO getUsuario() {return usuarioDto;}
}
