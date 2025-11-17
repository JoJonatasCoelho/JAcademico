package br.edu.ifce.jacademico.services;

// CustomUserDetailsService: bridges Usuario persistence to Spring Security authentication.
import br.edu.ifce.jacademico.entities.Usuario;
import br.edu.ifce.jacademico.repositories.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        GrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRole().name());
        return new User(usuario.getUsername(), usuario.getPassword(), Collections.singletonList(authority));
    }
}
