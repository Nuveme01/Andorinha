package model.seletor;

public class AbstractBaseSeletor {

	private Integer limite;
	private Integer pagina;

	public boolean possuiPaginacao() {
		return (this.limite != null && this.limite > 0) && (this.pagina != null && this.pagina > 0);
	}

	public Integer getOffset() {
		if (this.possuiPaginacao()) {
			return this.getLimite() * (this.getPagina() - 1);
		}
		return null;
	}

	public Integer getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public Integer getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

}
