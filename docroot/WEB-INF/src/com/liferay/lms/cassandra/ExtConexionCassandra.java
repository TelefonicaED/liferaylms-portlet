package com.liferay.lms.cassandra;



import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class ExtConexionCassandra  extends MVCPortlet{

	private static Cluster cluster;

	static String node="127.0.0.1";
	//static String node="10.102.227.59";
	public  static   Session session =null;
	
	
	
	//Learningactivitytry
	public static PreparedStatement countByact_uStatement;
	public static PreparedStatement countByact_Statement;
	public static PreparedStatement findByact_uStatement;
	public static PreparedStatement remove_uStatement;	
	public static PreparedStatement searchBylat_Statement;
	public static PreparedStatement searchByact_Statement;
	public static PreparedStatement getLastLearningActivityTryByActivityAndUser;
	public static PreparedStatement findByactByUserByEnDate_uStatement;
	public static PreparedStatement updateLearningActivityTry_Statement;

	
	
	
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
		      
		      session.execute(
			            "CREATE TABLE IF NOT EXISTS liferay.lms_learningactivitytry (" +
			                  "uuid_ varchar," + 
			                  "latId bigint," +
			                  "actId bigint," +		                  
			                  "userId bigint," + 
			                  "startDate timestamp," +		                  
			                  "result bigint," +
			                  "endDate varchar," +
			                  "tryData varchar," +
			                  "tryResultData varchar," +					                  
			                  "comments varchar," +					                  
			                  "PRIMARY KEY (latId)" + 
			                  ");");
		      
		      session.execute(
			            "CREATE INDEX IF NOT EXISTS sagi14 on liferay.lms_learningactivitytry (userId);");
		      session.execute(
			            "CREATE INDEX IF NOT EXISTS sagi15 on liferay.lms_learningactivitytry (endDate);");		      
		      

    //LEARNINGACTIVITYTRY
		      countByact_uStatement = session.prepare(
		    		  "select count(*) from liferay.lms_learningactivitytry WHERE actId = ? AND userId = ?  ALLOW FILTERING;");		      
		      countByact_Statement = session.prepare(
		    		  "select count(*) from liferay.lms_learningactivitytry WHERE actId = ?; ");		
		      findByact_uStatement= session.prepare(
		    		  "select * from liferay.lms_learningactivitytry WHERE actId = ? AND userId = ?  ALLOW FILTERING;");
		      remove_uStatement= session.prepare(
		    		  "delete  from liferay.lms_learningactivitytry where latId IN ?;;");
		      searchBylat_Statement =  session.prepare(
		    		  "select *  from liferay.lms_learningactivitytry where latId IN ?;");
		      
		      searchByact_Statement=  session.prepare(
		    		  "select *  from liferay.lms_learningactivitytry where actId= ?;");
		      
		      getLastLearningActivityTryByActivityAndUser = session.prepare(
		    		  "select *  from liferay.lms_learningactivitytry where latid=? and userId=? and endDate=? ALLOW FILTERING;");
		      
		      
		      
		      findByactByUserByEnDate_uStatement  = session.prepare(
		    		  "select *  from liferay.lms_learningactivitytry where actid=? and userId=? and endDate='' ALLOW FILTERING;");
		      
		      updateLearningActivityTry_Statement = session.prepare(
		    		  " INSERT INTO  liferay.lms_learningactivitytry (latid, actid, comments, enddate, result, startdate, trydata, tryresultdata, userid, uuid_) " 
		    			 + " VALUES (?,?,?,?,?,?,?,?,?,?);");	
		      

		      
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
