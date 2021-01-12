package model.dto;

import java.util.Calendar;

public class TweetDTO {

	private int id;
	private int idUsuario;

	private String conteudo;
	private Calendar data;

	public TweetDTO() {
		super();
	}

	public TweetDTO(int id, int idUsuario, String conteudo, Calendar data) {
		super();
		this.id = id;
		this.idUsuario = idUsuario;
		this.conteudo = conteudo;
		this.data = data;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public Calendar getData() {
		return data;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

}
