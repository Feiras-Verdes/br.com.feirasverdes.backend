package br.com.feirasverdes.backend.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import br.com.feirasverdes.backend.dao.AvaliacaoDao;
import br.com.feirasverdes.backend.dao.EnderecoDao;
import br.com.feirasverdes.backend.dao.FeiraDao;
import br.com.feirasverdes.backend.dao.NoticiaDao;
import br.com.feirasverdes.backend.dao.UsuarioDao;
import br.com.feirasverdes.backend.dto.FeiraDetalheDTO;
import br.com.feirasverdes.backend.dto.FeiraDto;
import br.com.feirasverdes.backend.dto.ListFeiraDTO;
import br.com.feirasverdes.backend.entidade.Endereco;
import br.com.feirasverdes.backend.entidade.Feira;
import br.com.feirasverdes.backend.entidade.Imagem;
import br.com.feirasverdes.backend.entidade.Usuario;
import br.com.feirasverdes.backend.exception.FeiraNaoPertenceAoUsuarioException;
import br.com.feirasverdes.backend.exception.UsuarioNaoEOrganizadorException;

@Service
@Transactional
public class FeiraService {

	@Autowired
	private FeiraDao dao;

	@Autowired
	private EnderecoDao enderecoDao;

	@Autowired
	private AvaliacaoDao avaliacaoDao;

	@Autowired
	private NoticiaDao noticiaDao;

	@Autowired
	private UsuarioDao usuarioDao;

	public Feira cadastrarFeira(Feira feira) throws IOException {
		return dao.save(feira);
	}

	public Feira atualizarFeira(FeiraDto feiraDto) throws IOException, FeiraNaoPertenceAoUsuarioException {
		Feira feira = dao.getOne(feiraDto.getId());

		if (!feira.getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			throw new FeiraNaoPertenceAoUsuarioException("Esta feira não foi cadastrada por você.");
		}
		paraFeira(feiraDto, feira);
		enderecoDao.save(feira.getEndereco());
		return dao.save(feira);
	}

	public void excluirFeira(Long id) throws FeiraNaoPertenceAoUsuarioException {
		Feira feira = dao.getOne(id);

		if (!feira.getUsuario().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
			throw new FeiraNaoPertenceAoUsuarioException("Esta feira não foi cadastrada por você.");
		}

		dao.deleteById(id);
	}

	private void paraFeira(FeiraDto feiraDto, Feira feira) throws IOException {
		feira.setHoraInicio(feiraDto.getHoraInicio());
		feira.setHoraFim(feiraDto.getHoraFim());
		feira.setFrequencia(feiraDto.getFrequencia());
		feira.setNome(feiraDto.getNome());
		feira.setUsuario(usuarioDao.getOne(feiraDto.getIdUsuario()));
		feira.setTelefone(feiraDto.getTelefone());
		feira.setEndereco(paraEndereco(feiraDto));

		if (feiraDto.getImagem() != null) {
			Imagem imagem = new Imagem();
			MultipartFile foto = feiraDto.getImagem();
			imagem.setNome(foto.getOriginalFilename());
			imagem.setTipo(foto.getContentType());
			imagem.setBytesImagem(foto.getBytes());
			feira.setImagem(imagem);
		}
	}

	private Endereco paraEndereco(FeiraDto feiraDto) {
		Endereco endereco = new Endereco();

		endereco.setBairro(feiraDto.getBairro());
		endereco.setCep(feiraDto.getCep());
		endereco.setCidade(feiraDto.getCidade());
		endereco.setComplemento(feiraDto.getComplemento());
		endereco.setEstado(feiraDto.getEstado());
		endereco.setNumero(feiraDto.getNumero());
		endereco.setLogradouro(feiraDto.getLogradouro());

		return endereco;
	}

	public List<Feira> getFeirasDeOrganizador(Long id)
			throws UsuarioNaoEOrganizadorException, IOException, DataFormatException {
		Optional<Usuario> usuario = usuarioDao.pesquisarPorId(id);

		if (!usuario.isPresent() || !usuario.get().getTipoUsuario().getDescricao().equals("ORGANIZADOR")) {
			throw new UsuarioNaoEOrganizadorException("Usuário de ID " + id + " não é um organizador.");
		}

		List<Feira> feiras = dao.buscarFeirasDeUsuario(id);
		return feiras;
	}

	public List<Feira> buscarFeirasPorUsuario(Long idUsuario) {
		return dao.findByUsuarioId(idUsuario);
	}

	public List<Feira> listarTodos() {
		return dao.findAll();
	}

	public List<Feira> pesquisarPorNome(String nome) {
		return dao.pesquisarPorNome(nome.toUpperCase());
	}

	public FeiraDetalheDTO pesquisarPorId(Long id) {
		Feira feira = dao.getOne(id);
		FeiraDetalheDTO feiraDto = new FeiraDetalheDTO();
		feiraDto.setId(feira.getId());
		feiraDto.setEndereco(feira.getEndereco());
		feiraDto.setFrequencia(feira.getFrequencia());
		feiraDto.setHoraFim(feira.getHoraFim());
		feiraDto.setHoraInicio(feira.getHoraInicio());
		feiraDto.setImagem(feira.getImagem());
		feiraDto.setNome(feira.getNome());
		feiraDto.setTelefone(feira.getTelefone());
		feiraDto.setUsuario(feira.getUsuario());
		Number avaliacao = dao.avaliacaoPorFeira(id);
		if (avaliacao != null) {
			feiraDto.setAvaliacao(avaliacao.doubleValue());
		}

		return feiraDto;
	}

	public List<ListFeiraDTO> buscarMelhoresFeiras() {
		return dao.buscarMelhoresFeiras();
	}

}
