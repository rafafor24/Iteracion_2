package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Zona;

public class DAOZona {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOZona
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOZona() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM ZONAS;
	 * @return Arraylist con los zonas de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Zona> darZonas() throws SQLException, Exception {
		ArrayList<Zona> zonas = new ArrayList<Zona>();

		String sql = "SELECT * FROM ZONAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			Integer espacio_abierto = rs.getInt("ESPACIO_ABIERTO");
			Integer capacidad = rs.getInt("CAPACIDAD");
			String apto_discapacitados = rs.getString("APTO_DISCAPACITADOS");
			String descripcion = rs.getString("DESCRIPCION");
			String especializacion = rs.getString("ESPECIALIZACION");
			zonas.add(new Zona(name, espacio_abierto,capacidad,apto_discapacitados,descripcion,especializacion));
		}
		return zonas;
	}


	/**
	 * Metodo que busca el/los zonas con el nombre que entra como parametro.
	 * @param name - Nombre de el/los zonas a buscar
	 * @return ArrayList con los zonas encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Zona> buscarZonasPorName(String name) throws SQLException, Exception {
		ArrayList<Zona> zonas = new ArrayList<Zona>();

		String sql = "SELECT * FROM ZONAS WHERE NOMBRE ='" + name + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			Integer espacio_abierto = rs.getInt("ESPACIO_ABIERTO");
			Integer capacidad = rs.getInt("CAPACIDAD");
			String apto_discapacitados = rs.getString("APTO_DISCAPACITADOS");
			String descripcion = rs.getString("DESCRIPCION");
			String especializacion = rs.getString("ESPECIALIZACION");
			zonas.add(new Zona(nombre, espacio_abierto,capacidad,apto_discapacitados,descripcion,especializacion));
		}

		return zonas;
	}


	/**
	 * Metodo que agrega el zona que entra como parametro a la base de datos.
	 * @param zona - el zona a agregar. zona !=  null
	 * <b> post: </b> se ha agregado el zona a la base de datos en la transaction actual. pendiente que el zona master
	 * haga commit para que el zona baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addZona(Zona zona) throws SQLException, Exception {

		String sql = "INSERT INTO ZONAS VALUES (";
		sql += "'"+zona.getNombre() + "',";
		sql += zona.getEspacio_abierto() + ",";
		sql += zona.getCapacidad() + ",";
		sql += "'"+zona.getApto_discapacitados() + "',";
		sql += "'"+zona.getDescripcion() + "',";
		sql += "'"+zona.getEspecializacion()+"'" + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el zona que entra como parametro en la base de datos.
	 * @param zona - el zona a actualizar. zona !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateZona(Zona zona) throws SQLException, Exception {

		String sql = "UPDATE ZONAS SET ";
		sql += "ESPACIO_ABIERTO=" + zona.getEspacio_abierto()+",";
		sql += "CAPACIDAD=" + zona.getCapacidad()+",";
		sql += "APTO_DISCAPACITADOS='" + zona.getApto_discapacitados()+"',";
		sql += "DESCRIPCION='" + zona.getDescripcion()+"',";
		sql += "ESPECIALIZACION='" + zona.getEspecializacion()+"',";
		sql += " WHERE NOMBRE='" + zona.getNombre()+"'";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el zona que entra como parametro en la base de datos.
	 * @param zona - el zona a borrar. zona !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteZona(Zona zona) throws SQLException, Exception {

		String sql = "DELETE FROM ZONAS";
		sql += " WHERE NOMBRE ='" + zona.getNombre()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}

