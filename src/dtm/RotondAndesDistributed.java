package dtm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

import jms.NonReplyException;
import jms.ProductosDisponiblesMBD;
import jms.RentabilidadRestauranteMBD;
import tm.RotondAndesTM;
import vos.Consulta1y2;
import vos.ListaProductos;
import vos.ListaRentabilidad;
import vos.Producto;
import vos.Rentabilidad;


public class RotondAndesDistributed 
{
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	
	private static RotondAndesDistributed instance;
	
	private RotondAndesTM tm;
	
	private QueueConnectionFactory queueFactory;
	
	private TopicConnectionFactory factory;
	
	private ProductosDisponiblesMBD productosMQ;
	
	private RentabilidadRestauranteMBD rentabilidadMQ;
	
	private static String path;


	private RotondAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		productosMQ = new ProductosDisponiblesMBD(factory, ctx);
		rentabilidadMQ = new RentabilidadRestauranteMBD(factory, ctx);
		productosMQ.start();
		rentabilidadMQ.start();
		
	}
	
	public void stop() throws JMSException
	{
		productosMQ.close();
		rentabilidadMQ.close();
	}
	
	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	public static void setPath(String p) {
		path = p;
	}
	
	public void setUpTransactionManager(RotondAndesTM tm)
	{
	   this.tm = tm;
	}
	
	private static RotondAndesDistributed getInst()
	{
		return instance;
	}
	
	public static RotondAndesDistributed getInstance(RotondAndesTM tm)
	{
		if(instance == null)
		{
			try {
				instance = new RotondAndesDistributed();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		instance.setUpTransactionManager(tm);
		return instance;
	}
	
	public static RotondAndesDistributed getInstance()
	{
		if(instance == null)
		{
			RotondAndesTM tm = new RotondAndesTM(path);
			return getInstance(tm);
		}
		if(instance.tm != null)
		{
			return instance;
		}
		RotondAndesTM tm = new RotondAndesTM(path);
		return getInstance(tm);
	}
	

	public ListaProductos getLocalProductosDisponibles() throws Exception
	{
		return tm.darProductosDisponibles();
	}
	
	public ListaProductos getRemoteProductosDisponibles() throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return productosMQ.getRemoteVideos();
	}

	
	public ListaRentabilidad getLocalRentabilidad() throws Exception
	{
	Consulta1y2 cons= new Consulta1y2("01/02/2017","01/03/2017", "xyz");	
		return tm.darRentabilidad(cons);
	}
	
	public ListaRentabilidad getRemotRentabilidad() throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return rentabilidadMQ.getRemoteRentabilidad();
	}
}
