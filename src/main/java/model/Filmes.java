package model;

import com.google.gson.annotations.SerializedName;

public class Filmes {
	private int id;

	private String title;

	@SerializedName("overview")
	private String sinopse;

	@SerializedName("release_date")
	private String dataLancamento;

	@SerializedName("vote_average")
	private double nota;

	public Filmes() {

	}

	public Filmes(int id, String title, String sinopse, String dataLancamento, double nota) {
		this.id = id;
		this.title = title;
		this.sinopse = sinopse;
		this.dataLancamento = dataLancamento;
		this.nota = nota;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public String getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(String dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	@Override
	public String toString() {
		return "--------------------------------------------------\n" +
				"🆔 ID: " + id + "  <-- (USE ESTE NÚMERO PARA EXCLUIR/ATUALIZAR)\n" +
				"🎬 Título: " + title + "\n" +
				"📅 Lançamento: " + dataLancamento + " | ⭐ Nota: " + nota + "\n" +
				"📝 Sinopse: " + sinopse + "\n" +
				"--------------------------------------------------";
	}
}
