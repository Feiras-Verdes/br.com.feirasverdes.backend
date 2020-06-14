package br.com.feirasverdes.backend.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserContext extends User {

	private static final long serialVersionUID = 1L;

	private final Long idUsuario;

	public UserContext(final Long idUsuario, final String email, final String senha,
			final Collection<? extends GrantedAuthority> authorities) {
		super(email, senha, authorities);
		this.idUsuario = idUsuario;
	}

	public UserContext(final Long userId, final String username, final String password, final boolean enabled,
			final boolean accountNonExpired, final boolean credentialsNonExpired, final boolean accountNonLocked,
			final Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.idUsuario = userId;
	}

	public Long getidUsuario() {
		return idUsuario;
	}

}
