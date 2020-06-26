package br.com.feirasverdes.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.feirasverdes.backend.dao.TipoUsuarioDao;
import br.com.feirasverdes.backend.dao.UsuarioDao;
import br.com.feirasverdes.backend.entidade.TipoUsuario;
import br.com.feirasverdes.backend.entidade.Usuario;

@Component
@Transactional
public class UsuarioTestUtil {

	@Autowired
	private UsuarioDao dao;
	
	@Autowired
	private TipoUsuarioDao tipoUsuarioDao;
	
	public Usuario criarUsuarioLogin(String email, String senha, Long tipoUsuario) {
		Usuario usuario = dao.pesquisarPorEmail(email);
		if (usuario != null) {
			return usuario;
		}
		usuario = new Usuario();
		usuario.setNome(email);
		usuario.setSenha(senha);
		usuario.setEmail(email);
		usuario.setAtivo(true);
		usuario.setTipoUsuario(tipoUsuarioDao.getOne(tipoUsuario));
		usuario.setCpf("000.000.000-00");
		return dao.save(usuario);
	}

}
