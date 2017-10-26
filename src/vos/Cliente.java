package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Cliente extends Usuario 
{

	/**
	 * id del cliente
	 */
	@JsonProperty(value="id")
	private Long id;
	
	/**
	 * Id de usuario del cliente
	 */
	@JsonProperty(value="id_usuario")
	private Long id_usuario;
	
	public Cliente(String nombre, Integer identificacion, String correo_electronico,@JsonProperty(value="id")Long id,
			@JsonProperty(value="id_usuario") Long id_usuario) {
		super(nombre, identificacion, "Cliente", correo_electronico);
		this.setId(id);
		this.setId_usuario(id_usuario);
	}

	public Cliente(@JsonProperty(value="id")Long id,
			@JsonProperty(value="id_usuario") Long id_usuario) {
		super(" ", 0, "Cliente", " ");
		this.setId(id);
		this.setId_usuario(id_usuario);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}

}
