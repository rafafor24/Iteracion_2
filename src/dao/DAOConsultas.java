package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Cliente;
import vos.Consulta1y2;
import vos.Rentabilidad;
import vos.ResultadoConsulta3;
import vos.Usuario;

public class DAOConsultas {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOCliente
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOConsultas() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los clientes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM CLIENTES;
	 * @return Arraylist con los clientes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Usuario> consultar(Consulta1y2 consulta,Long cantidad) throws SQLException, Exception {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		String poner="!";
		if(cantidad==1)
		{
			poner=" ";
		}

		String sql = "select nombre,identificacion ,rol,CORREO_ELECTRONICO from pedidos"
				+ " inner join (select servicios.representante_restaurante as represt, servicios.id_pedido as idped,"
				+ " usuarios.nombre as nombre,usuarios.identificacion as identificacion,usuarios.rol as rol,"
				+ "usuarios.CORREO_ELECTRONICO as CORREO_ELECTRONICO from servicios inner join usuarios "
				+ "on (servicios.NOMBRE_USUARIO=usuarios.NOMBRE)) x on(x.idped=pedidos.ID) "
				+ "where (pedidos.FECHA BETWEEN '"+consulta.getFecha1()+"' AND '"+consulta.getFecha2()+"') AND x.represt"+poner+"='"+consulta.getRepresentante()+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			Integer identificacion = rs.getInt("IDENTIFICACION");
			String rol = rs.getString("ROL");
			String correo_electronico= rs.getString("CORREO_ELECTRONICO");
			usuarios.add(new Usuario(name, identificacion,rol, correo_electronico));
		}
		return usuarios;
	}
	
	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los clientes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM CLIENTES;
	 * @return Arraylist con los clientes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Usuario> consultarClientesBuenos() throws SQLException, Exception {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
	

		String sql = "select * from USUARIOS where NOMBRE IN(select NOMUSER "
				+ "from PRODUCTOS inner join (select NOMPROD,NOMUSER from USUARIOS inner join"
				+ " (select Producto_pedido.NOMBRE_PRODUCTO as NOMPROD,SERVICIOS.NOMBRE_USUARIO as NOMUSER "
				+ "from producto_pedido inner join SERVICIOS ON(SERVICIOS.ID_PEDIDO=producto_pedido.ID_PEDIDO)) tabla "
				+ "ON(USUARIOS.NOMBRE=tabla.NOMUSER) where usuarios.rol='Cliente')tabla2  ON(tabla2.NOMPROD=PRODUCTOS.NOMBRE) "
				+ "group by(NOMUSER) having AVG(PRODUCTOS.PRECIO_VENTA)>50000000)";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NOMBRE");
			Integer identificacion = rs.getInt("IDENTIFICACION");
			String rol = rs.getString("ROL");
			String correo_electronico= rs.getString("CORREO_ELECTRONICO");
			usuarios.add(new Usuario(name, identificacion,rol, correo_electronico));
		}
		return usuarios;
	}
	
	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los clientes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM CLIENTES;
	 * @return Arraylist con los clientes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ResultadoConsulta3> consultarDias() throws SQLException, Exception {
		ArrayList<ResultadoConsulta3> resultados = new ArrayList<ResultadoConsulta3>();
	

		String sql = "select * from USUARIOS where NOMBRE IN(select NOMUSER "
				+ "from PRODUCTOS inner join (select NOMPROD,NOMUSER from USUARIOS inner join"
				+ " (select Producto_pedido.NOMBRE_PRODUCTO as NOMPROD,SERVICIOS.NOMBRE_USUARIO as NOMUSER "
				+ "from producto_pedido inner join SERVICIOS ON(SERVICIOS.ID_PEDIDO=producto_pedido.ID_PEDIDO)) tabla "
				+ "ON(USUARIOS.NOMBRE=tabla.NOMUSER) where usuarios.rol='Cliente')tabla2  ON(tabla2.NOMPROD=PRODUCTOS.NOMBRE) "
				+ "group by(NOMUSER) having AVG(PRODUCTOS.PRECIO_VENTA)>50000000)";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String dia = rs.getString("DIA");
			String prodmas = rs.getString("NOMBRE_PRODUCTO");
			String prodmenos = rs.getString("NOMBRE_PRODUCTO");
			String restmas= rs.getString("REPRESENTANTE_RESTAURANTE");
			String restmenos= rs.getString("REPRESENTANTE_RESTAURANTE");
			resultados.add(new ResultadoConsulta3(dia,prodmas,prodmenos,restmas,restmenos));
		}
		return resultados;
	}
	
	
	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los clientes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM CLIENTES;
	 * @return Arraylist con los clientes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Rentabilidad> darRentabilidad(Consulta1y2 cons) throws SQLException, Exception {
		ArrayList<Rentabilidad> resultados = new ArrayList<Rentabilidad>();
		String representante = cons.getRepresentante();
		String fecha1 =cons.getFecha1();
		String fecha2 = cons.getFecha2();
		
		String sql = "SELECT *"+
				"FROM"+
				" (SELECT B.NOMBRE_ZONA AS NOMBRE,"+
				" SUM(A.PRECIO_VENTA) AS INGRESOS,"+
				" (CASE"+
				" WHEN 1=1 THEN 'Nombre de Zona'"+
				" ELSE 'Chao'"+
				"  END) AS TIPO"+
				"  FROM"+
				"  (SELECT PRODUCTO_PEDIDO.ID_PEDIDO,"+
				" PRODUCTO_PEDIDO.NOMBRE_PRODUCTO,"+
				"  PRODUCTOS.PRECIO_VENTA"+
				" FROM PRODUCTO_PEDIDO"+
				"  INNER JOIN PRODUCTOS ON(PRODUCTO_PEDIDO.NOMBRE_PRODUCTO=PRODUCTOS.NOMBRE)) A"+
				"  INNER JOIN"+
				"  (SELECT SERVICIOSZOONA.ID_PEDIDO,"+
				"  PEDIDOS.FECHA,"+
				" PEDIDOS.NOMBRE_USUARIO,"+
				"  SERVICIOSZOONA.REPREST,"+
				"  SERVICIOSZOONA.NOMBRE_ZONA"+
				" FROM PEDIDOS"+
				" INNER JOIN"+
				" (SELECT SERVICIOS.REPRESENTANTE_RESTAURANTE AS REPREST,"+
				"   NOMBRE_ZONA,"+
				"  ID_PEDIDO,"+
				"  NOMBRE_USUARIO"+
				" FROM SERVICIOS"+
				"  INNER JOIN RESTAURANTE_ZONA ON(SERVICIOS.REPRESENTANTE_RESTAURANTE = RESTAURANTE_ZONA.REPRESENTANTE_RESTAURANTE)) SERVICIOSZOONA ON(SERVICIOSZOONA.ID_PEDIDO=PEDIDOS.ID)"+
				"  WHERE SERVICIOSZOONA.REPREST='"+representante+"'"+
				" AND (PEDIDOS.FECHA BETWEEN '"+fecha1+"' AND '"+fecha2+"')) B ON(A.ID_PEDIDO=B.ID_PEDIDO)"+
				" GROUP BY B.NOMBRE_ZONA)"+
				" UNION"+
				" (SELECT A.NOMBRE_PRODUCTO AS NOMBRE,"+
				"SUM(A.PRECIO_VENTA) AS INGRESOS,"+
				"   (CASE"+
				"     WHEN 1=1 THEN 'Nombre de Producto'"+
				"       ELSE 'Chao'"+
				"     END) AS TIPO"+
				" FROM"+
				" (SELECT PRODUCTO_PEDIDO.ID_PEDIDO,"+
				"   PRODUCTO_PEDIDO.NOMBRE_PRODUCTO,"+
				"    PRODUCTOS.PRECIO_VENTA"+
				"   FROM PRODUCTO_PEDIDO"+
				"  INNER JOIN PRODUCTOS ON(PRODUCTO_PEDIDO.NOMBRE_PRODUCTO=PRODUCTOS.NOMBRE)) A"+
				" INNER JOIN"+
				" (SELECT SERVICIOSZOONA.ID_PEDIDO,"+
				"      PEDIDOS.FECHA,"+
				"     PEDIDOS.NOMBRE_USUARIO,"+
				"     SERVICIOSZOONA.REPREST,"+
				"      SERVICIOSZOONA.NOMBRE_ZONA"+
				" FROM PEDIDOS"+
				" INNER JOIN"+
				"  (SELECT SERVICIOS.REPRESENTANTE_RESTAURANTE AS REPREST,"+
				"        NOMBRE_ZONA,"+
				"        ID_PEDIDO,"+
				"        NOMBRE_USUARIO"+
				"   FROM SERVICIOS"+
				"   INNER JOIN RESTAURANTE_ZONA ON(SERVICIOS.REPRESENTANTE_RESTAURANTE = RESTAURANTE_ZONA.REPRESENTANTE_RESTAURANTE)) SERVICIOSZOONA ON(SERVICIOSZOONA.ID_PEDIDO=PEDIDOS.ID)"+
				"   WHERE SERVICIOSZOONA.REPREST='"+representante+"'"+
				"     AND (PEDIDOS.FECHA BETWEEN '"+fecha1+"' AND '"+fecha2+"')) B ON(A.ID_PEDIDO=B.ID_PEDIDO)"+
				" GROUP BY A.NOMBRE_PRODUCTO)"+
				"UNION"+
				"(SELECT AA.TIPO_COMIDA AS NOMBRE,"+
				"     SUM(BB.PRECIO_VENTA) AS INGRESOS,"+
				"     (CASE"+
				"          WHEN 1=1 THEN 'Tipo de Comida'"+
				"          ELSE 'Chao'"+
				"      END) AS TIPO FROM"+
				" (SELECT A.ID_PEDIDO, A.REPREST,RESTAURANTES.TIPO_COMIDA"+
				"  FROM"+
				"   (SELECT SERVICIOSZOONA.ID_PEDIDO,PEDIDOS.FECHA,PEDIDOS.NOMBRE_USUARIO, SERVICIOSZOONA.REPREST,SERVICIOSZOONA.NOMBRE_ZONA"+
				"   FROM PEDIDOS"+
				"    INNER JOIN"+
				"    (SELECT SERVICIOS.REPRESENTANTE_RESTAURANTE AS REPREST,NOMBRE_ZONA,ID_PEDIDO,NOMBRE_USUARIO"+
				"    FROM SERVICIOS"+
				"     INNER JOIN RESTAURANTE_ZONA ON(SERVICIOS.REPRESENTANTE_RESTAURANTE = RESTAURANTE_ZONA.REPRESENTANTE_RESTAURANTE)) SERVICIOSZOONA ON(SERVICIOSZOONA.ID_PEDIDO=PEDIDOS.ID)"+
				"  WHERE SERVICIOSZOONA.REPREST='"+representante+"'"+
				"    AND (PEDIDOS.FECHA BETWEEN '"+fecha1+"' AND '"+fecha2+"'))A"+
				"  INNER JOIN RESTAURANTES ON(A.REPREST=RESTAURANTES.REPRESENTANTE)) AA"+
				" INNER JOIN"+
				"  (SELECT PRODUCTO_PEDIDO.ID_PEDIDO,"+
				"       PRODUCTO_PEDIDO.NOMBRE_PRODUCTO,"+
				"       PRODUCTOS.PRECIO_VENTA"+
				"  FROM PRODUCTO_PEDIDO"+
				"   INNER JOIN PRODUCTOS ON(PRODUCTO_PEDIDO.NOMBRE_PRODUCTO=PRODUCTOS.NOMBRE)) BB ON(AA.ID_PEDIDO=BB.ID_PEDIDO)"+
				"  GROUP BY AA.TIPO_COMIDA)";
		
		System.out.println("super SQL ="+sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String dia = rs.getString("NOMBRE");
			Long prodmas = rs.getLong("INGRESOS");
			String prodmenos = rs.getString("TIPO");
			
			resultados.add(new Rentabilidad(dia,prodmas,prodmenos));
		}
		return resultados;
	}

}
