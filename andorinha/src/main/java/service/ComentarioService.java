package service;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Comentario;
import model.dto.ComentarioDTO;
import model.exceptions.ErroAoConectarNaBaseException;
import model.exceptions.ErroAoConsultarBaseException;
import model.seletor.ComentarioSeletor;
import repository.ComentarioRepository;

@Path("/comentario")
public class ComentarioService {

	@EJB
	ComentarioRepository comentarioRepository;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comentario> listarTodos() throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		return this.comentarioRepository.listarTodos();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Comentario inserir(Comentario comentario)
			throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		this.comentarioRepository.inserir(comentario);
		return comentario;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Comentario consultar(@PathParam("id") Integer id)
			throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		return this.comentarioRepository.consultar(id);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void atualizar(Comentario comentario) throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		this.comentarioRepository.atualizar(comentario);
	}

	@DELETE
	@Path("/{id}")
	public void deletar(@PathParam("id") Integer id)
			throws ErroAoConectarNaBaseException, ErroAoConsultarBaseException {
		this.comentarioRepository.remover(id);
	}

	@POST
	@Path("/pesquisar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Comentario> pesquisar(ComentarioSeletor seletor)
			throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		return this.comentarioRepository.pesquisar(seletor);
	}
	
	@POST
	@Path("/dto")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<ComentarioDTO> pesquisarDTO(ComentarioSeletor seletor)
			throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		return this.comentarioRepository.pesquisarDTO(seletor);
	}
}
