package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cliente extends Usuario 
{

	/**
	 * id del cliente
	 */
	@JsonProperty(value="id")
	private Long id;
	
	public Cliente(@JsonProperty(value="nombre")String nombre, 
			@JsonProperty(value="identificacion")Integer identificacion, 
			@JsonProperty(value="correo_electronico")String correo_electronico,
			@JsonProperty(value="id")Long id)
	{
		super(nombre, identificacion, "Cliente", correo_electronico);
		this.setId(id);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
