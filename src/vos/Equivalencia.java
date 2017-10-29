package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Equivalencia
{

	/**
	 * id de lA equivalencia
	 */
	@JsonProperty(value="id1")
	private Long id1;
	
	/**
	 * id2 de lA equivalencia
	 */
	@JsonProperty(value="id2")
	private Long id2;
	
	/**
	 * id2 de lA equivalencia
	 */
	@JsonProperty(value="tipo")
	private String tipo;
	
	public Equivalencia(@JsonProperty(value="id2")Long id2,
			@JsonProperty(value="id1")Long id1,
			@JsonProperty(value="tipo")String tipo)
	{
		
		this.setId1(id1);
		this.setId2(id2);
		this.setTipo(tipo);
	}

	public Long getId1() {
		return id1;
	}

	public void setId1(Long id) {
		this.id1 = id;
	}

	public Long getId2() {
		return id2;
	}

	public void setId2(Long id2) {
		this.id2 = id2;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
