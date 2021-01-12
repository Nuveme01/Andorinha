package repository;

import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import model.Tweet;
import model.dto.TweetDTO;
import model.exceptions.ErroAoConectarNaBaseException;
import model.exceptions.ErroAoConsultarBaseException;
import model.seletor.TweetSeletor;

@Stateless
public class TweetRepository extends AbstractCrudRepository {

	public void inserir(Tweet tweet) throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		tweet.setData(Calendar.getInstance());
		super.em.persist(tweet);
	}

	public void atualizar(Tweet tweet) throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		tweet.setData(Calendar.getInstance());
		super.em.merge(tweet);
	}

	public void remover(int id) throws ErroAoConectarNaBaseException, ErroAoConsultarBaseException {
		Tweet t = this.consultar(id);
		super.em.remove(t);
	}

	public Tweet consultar(int id) throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		return super.em.find(Tweet.class, id);
	}

	public List<Tweet> listarTodos() throws ErroAoConsultarBaseException, ErroAoConectarNaBaseException {
		return this.pesquisar(new TweetSeletor());
	}

	public List<Tweet> pesquisar(TweetSeletor seletor) {
		StringBuilder jpql = new StringBuilder();

		jpql.append("SELECT t FROM Tweet t ");
		jpql.append("INNER JOIN FETCH t.usuario ");

		this.criarFiltro(jpql, seletor);

		Query query = super.em.createQuery(jpql.toString());

		this.adicionarParamentros(query, seletor);

		return query.getResultList();
	}

	public List<TweetDTO> pesquisarDTO(TweetSeletor seletor) {
		StringBuilder jpql = new StringBuilder();

		jpql.append("SELECT new model.dto.TweetDTO( t.id, u.id, t.conteudo, t.data ) ");
		jpql.append("FROM Tweet t ");
		jpql.append("INNER JOIN t.usuario u ");

		this.criarFiltro(jpql, seletor);

		Query query = super.em.createQuery(jpql.toString());

		this.adicionarParamentros(query, seletor);

		return query.getResultList();
	}

	private void adicionarParamentros(Query query, TweetSeletor seletor) {
		if (seletor.possuiFiltro()) {
			if (seletor.getId() != null) {
				query.setParameter("id", seletor.getId());
			}
			if (seletor.getIdUsuario() != null) {
				query.setParameter("id_usuario", seletor.getIdUsuario());
			}
		}

	}

	public void criarFiltro(StringBuilder jpql, TweetSeletor seletor) {
		if (seletor.possuiFiltro()) {
			jpql.append("WHERE ");
			boolean primeiraVez = true;
			if (seletor.getId() != null) {
				jpql.append("t.id = :id ");
			}

			if (seletor.getIdUsuario() != null) {
				if (!primeiraVez) {
					jpql.append("AND ");
				}
				jpql.append("t.usario.id = :id_usuario ");
			}
		}
	}

}
