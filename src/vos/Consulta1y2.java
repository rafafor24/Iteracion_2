package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Consulta1y2 {

	@JsonProperty(value="fecha1")
	private String fecha1;
	
	@JsonProperty(value="fecha2")
	private String fecha2;
	
	@JsonProperty(value="representante")
	private String representante;
	
	
	public Consulta1y2(@JsonProperty(value="fecha1")String fecha1, 
			@JsonProperty(value="fecha2")String fecha2, 
			@JsonProperty(value="representante")String representante)
	{
		
		this.fecha1=fecha1;
		this.fecha2=fecha2;
		this.representante=representante;
	}

	public String getFecha1() {
		return fecha1;
	}

	public void setFecha1(String fecha1) {
		this.fecha1 = fecha1;
	}

	public String getFecha2() {
		return fecha2;
	}

	public void setFecha2(String fecha2) {
		this.fecha2 = fecha2;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}


	
	
}



