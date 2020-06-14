package br.com.feirasverdes.backend.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.feirasverdes.backend.config.UserContext;
import br.com.feirasverdes.backend.dao.UsuarioDao;
import br.com.feirasverdes.backend.entidade.Usuario;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	private static final String PREFIXO_ROLE = "ROLE_";

	@Autowired
	private UsuarioDao dao;

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		Usuario usuario = dao.pesquisarPorEmail(email);

		if (usuario != null) {
			return new UserContext(usuario.getId(), usuario.getEmail(), usuario.getSenha(),
					getAuthorities(usuario.getTipoUsuario().getDescricao()));
		} else {
			throw new UsernameNotFoundException("Usuário com não encontrado: " + email);
		}
	}

	public Collection<? extends GrantedAuthority> getAuthorities(String tipoUsuario) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

		list.add(new SimpleGrantedAuthority(PREFIXO_ROLE + tipoUsuario));

		return list;
	}

}