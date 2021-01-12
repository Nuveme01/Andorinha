package repository;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import model.Comentario;
import model.dto.ComentarioDTO;
import model.exceptions.ErroAoConectarNaBaseException;
import model.exceptions.ErroAoConsultarBaseException;
import model.seletor.ComentarioSeletor;

@Stateless
public class ComentarioRepository extends AbstractCrudRepository {

	public void inserir(Comentario comentario) {
		super.em.persist(comentario);
	}

	public void atualizar(Comentario comentario) {
		super.em.merge(comentario);
	}

	public void remover(int id) {
		Comentario comentario = super.em.find(Comentario.class, id);
		super.em.remove(comentario);
	}

	public Comentario consultar(int id) {
		return super.em.find(Comentario.class, id);
	}

	public List<Comentario> pesquisar(ComentarioSeletor seletor)
			throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {

		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT c FROM Comentario c ");
		jpql.append("INNER JOIN FETCH c.usuario ");
		jpql.append("INNER JOIN FETCH c.tweet t ");
		jpql.append("INNER JOIN FETCH t.usuario ");

		this.criarFiltro(jpql, seletor);

		Query query = super.em.createQuery(jpql.toString());

		this.adicionarParametros(query, seletor);

		return query.getResultList();
	}

	public List<ComentarioDTO> pesquisarDTO(ComentarioSeletor seletor)
			throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {

		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT new model.dto.ComentarioDTO(c.id, t.id, u.id, u.nome, c.data, c.conteudo) ");
		jpql.append("FROM Comentario c ");
		jpql.append("INNER JOIN c.usuario u ");
		jpql.append("INNER JOIN c.tweet t ");
		jpql.append("INNER JOIN t.usuario ");

		this.criarFiltro(jpql, seletor);

		Query query = super.em.createQuery(jpql.toString());

		this.adicionarParametros(query, seletor);

		return query.getResultList();
	}

	private void adicionarParametros(Query query, ComentarioSeletor seletor) {

		if (seletor.possuiFiltro()) {
			if (seletor.getIdUsuario() != null) {
				query.setParameter("id_usuario", seletor.getIdUsuario());
			}

			if (seletor.getIdTweet() != null) {
				query.setParameter("id_tweet", seletor.getIdTweet());
			}
		}

	}

	private void criarFiltro(StringBuilder jpql, ComentarioSeletor seletor) {
		if (seletor.possuiFiltro()) {
			jpql.append("WHERE ");
			boolean primeiro = true;
			if (seletor.getIdUsuario() != null) {
				jpql.append("c.usuario.id = :id_usuario ");
			}

			if (seletor.getIdTweet() != null) {
				if (primeiro) {
					jpql.append("AND ");
				}
				jpql.append("c.tweet.id = :id_tweet ");
			}

		}
	}

	public Long contar(ComentarioSeletor seletor) throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {

		StringBuilder jpql = new StringBuilder();

		jpql.append("SELECT COUNT(c) FROM Comentario c ");

		this.criarFiltro(jpql, seletor);

		Query query = super.em.createQuery(jpql.toString());

		this.adicionarParametros(query, seletor);

		return (Long) query.getSingleResult();
	}

	public List<Comentario> listarTodos() throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		return this.pesquisar(new ComentarioSeletor());
	}

}