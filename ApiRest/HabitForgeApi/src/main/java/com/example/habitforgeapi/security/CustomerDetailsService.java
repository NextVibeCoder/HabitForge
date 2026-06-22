package com.example.habitforgeapi.security;




import com.example.habitforgeapi.model.Usuario;
import com.example.habitforgeapi.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomerDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    private Usuario usuarioDetail;

    public CustomerDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        usuarioDetail = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new org.springframework.security.core.userdetails.User(
                usuarioDetail.getEmail(),
                usuarioDetail.getPasswordHash(),
                Collections.emptyList()
        );
    }

    public Usuario getUserDetail() {
        return usuarioDetail;
    }
}
