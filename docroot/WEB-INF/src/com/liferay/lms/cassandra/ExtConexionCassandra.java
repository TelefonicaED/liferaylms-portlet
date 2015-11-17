package com.liferay.lms.cassandra;



import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class ExtConexionCassandra  extends MVCPortlet{

	private static Cluster cluster;

	static String node="127.0.0.1";
	//static String node="10.102.227.59";
	public  static   Session session =null;
	

	
	
	   public static void createSchema() {
		   

		      session.execute("CREATE KEYSPACE IF NOT EXISTS liferay WITH replication " + 
			            "= {'class':'SimpleStrategy', 'replication_factor':1};");

				      
		      session.execute(
			            "CREATE TABLE IF NOT EXISTS liferay.auditentry (" +
			                  "auditId bigint," + 
			                  "auditDate timestamp," +
			                  "companyId bigint," +		                  
			                  "groupId bigint," + 
			                  "userId bigint," +		                  
			                  "classname varchar," +
			                  "action varchar," +
			                  "extraData varchar," +
			                  "classPK bigint," +					                  
			                  "association bigint," +					                  
			                  "PRIMARY KEY (auditId)" + 
			                  ");");				      
		      
		   }	
	

	   public static void connect(){
		  if(cluster==null){
		      cluster = Cluster.builder()
		            .addContactPoint(node)
		            .build();
		      Metadata metadata = cluster.getMetadata();
		      System.out.printf("Connected to cluster: %s\n", 
		            metadata.getClusterName());
		      for ( Host host : metadata.getAllHosts() ) {
		         System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n",
		               host.getDatacenter(), host.getAddress(), host.getRack());
		      }
		      
		      session = cluster.connect();
		  }
		   
	  }


	 public void init() {
			// TODO Auto-generated method stub
			connect();
		    createSchema();			

		// TODO Auto-generated method stub

	}
	

}
