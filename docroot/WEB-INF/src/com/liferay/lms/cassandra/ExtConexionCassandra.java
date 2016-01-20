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
	
	
	
	//LeareningActivityResult
	
	public static PreparedStatement countByact_user;
	public static PreparedStatement fetchByact_user;
	public static PreparedStatement updateLearningActivityResult_Statement;
	public static PreparedStatement searchBylar_Statement;
	public static PreparedStatement countByap;
	public static PreparedStatement countByuser;
	public static PreparedStatement countAll;
	public static PreparedStatement countByac;
	public static PreparedStatement countByap_endDate;
	public static PreparedStatement countByUuid;
	
	

	public static PreparedStatement searchByActId_userId_Statement;
	public static PreparedStatement searchByActId_userId_passedStatement;
	public static PreparedStatement searchByActId_NotpassedStatement;
	public static PreparedStatement avgResult_Statement;
	public static PreparedStatement getLastEndDateByUserId_Statement;
	public static PreparedStatement getByActId_Statement;
	public static PreparedStatement remove_Statement;
	

	
	
	
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
			                  "endDate timestamp," +
			                  "tryData varchar," +
			                  "tryResultData varchar," +					                  
			                  "comments varchar," +					                  
			                  "PRIMARY KEY (latId)" + 
			                  ");");
		      
		      session.execute(
			            "CREATE TABLE IF NOT EXISTS liferay.lms_learningactivityResult (" +
			                  "uuid_ varchar," + 
			                  "larId bigint," +
			                  "actId bigint," +		                  
			                  "userId bigint," +
			                  "result bigint," +			                  
			                  "startDate timestamp," +		                  
			                  "endDate timestamp," +
			                  "latId bigint," +					                  
			                  "comments varchar," +					                  
			                  "passed boolean," +			                  
			                  "PRIMARY KEY (larId)" + 
	                          ")"); 
	      
		      session.execute(
			            "CREATE INDEX IF NOT EXISTS sagi14 on liferay.lms_learningactivitytry (userId);");
		      session.execute(
			            "CREATE INDEX IF NOT EXISTS sagi15 on liferay.lms_learningactivitytry (endDate);");	
		      session.execute(
			            "CREATE INDEX IF NOT EXISTS sagi16 on liferay.lms_learningactivityresult (endDate);");
			            			      
		      

    //LEARNINGACTIVITYTRY
		      countByact_uStatement = session.prepare(
		    		  "select count(*) from liferay.lms_learningactivitytry WHERE actId = ? AND userId = ?  ALLOW FILTERING;");		      
		      countByact_Statement = session.prepare(
		    		  "select count(*) from liferay.lms_learningactivitytry WHERE actId = ?  ALLOW FILTERING;; ");		
		      findByact_uStatement= session.prepare(
		    		  "select * from liferay.lms_learningactivitytry WHERE actId = ? AND userId = ?  ALLOW FILTERING;");
		      remove_uStatement= session.prepare(
		    		  "delete  from liferay.lms_learningactivitytry where latId =?;");
		      searchBylat_Statement =  session.prepare(
		    		  "select *  from liferay.lms_learningactivitytry where latId= ?;");

		      
		      searchByact_Statement=  session.prepare(
		    		  "select *  from liferay.lms_learningactivitytry where actId= ?  ALLOW FILTERING;");	  
		      
		      getLastLearningActivityTryByActivityAndUser = session.prepare(
		    		  "select *  from liferay.lms_learningactivitytry where latid=? and userId=? and endDate=? ALLOW FILTERING;");
		      
		      
		      
		      findByactByUserByEnDate_uStatement  = session.prepare(
		    		  "select *  from liferay.lms_learningactivitytry where actid=? and userId=? and endDate='' ALLOW FILTERING;");
		      
		      updateLearningActivityTry_Statement = session.prepare(
		    		  " INSERT INTO  liferay.lms_learningactivitytry (latid, actid, comments, enddate, result, startdate, trydata, tryresultdata, userid, uuid_) " 
		    			 + " VALUES (?,?,?,?,?,?,?,?,?,?);");
		      

		      

	 //LEARNINGACTIVITYRESULT
		      countByact_user = session.prepare(
		    		  "select count(*) from liferay.lms_learningactivityResult WHERE actId = ? AND userId = ?  ALLOW FILTERING;");	
		      
		      fetchByact_user = session.prepare(
		    		  "select * from liferay.lms_learningactivityResult WHERE actId = ? AND userId = ?  ALLOW FILTERING;");
		      
		      updateLearningActivityResult_Statement= session.prepare(
		    		  " INSERT INTO  liferay.lms_learningactivityResult (uuid_, larId, actid,userid,result,startdate,enddate,latid, comments, passed) " 
		    			 + " VALUES (?,?,?,?,?,?,?,?,?,?);");
		      searchBylar_Statement =  session.prepare(
		    		  "select *  from liferay.lms_learningactivityResult where larId= ?;");
		      
		      countByap =  session.prepare(
		    		  "select count(*)  from liferay.lms_learningactivityResult where actId= ?  AND passed = ?  ALLOW FILTERING;");
		      countByap_endDate =  session.prepare(
		    		  "select count(*)  from liferay.lms_learningactivityResult where actId= ?  AND passed = ?  and endDate = ? ALLOW FILTERING;");

		      
		      searchByActId_userId_Statement = session.prepare(
		    		  "select *  from liferay.lms_learningactivityResult where actId= ? and userId = ?  ALLOW FILTERING;");
		      
	      
		      
		      searchByActId_userId_passedStatement = session.prepare(
		    		  "select *  from liferay.lms_learningactivityResult where actId= ? and userId = ? and passed = ?  ALLOW FILTERING;");
		      
		      searchByActId_NotpassedStatement = session.prepare(
		    		  "select *  from liferay.lms_learningactivityResult where actId= ?  and passed = false ALLOW FILTERING;");		
		      
		      avgResult_Statement =  session.prepare(
		    		  "select avg(result)  from liferay.lms_learningactivityResult where actId= ?  ALLOW FILTERING;");		
		      
		      
		      getLastEndDateByUserId_Statement  = session.prepare(
		    		  "select *  from liferay.lms_learningactivityResult where userId= ?  ALLOW FILTERING;");		
		      
		      getByActId_Statement = session.prepare(
		    		  "select *  from liferay.lms_learningactivityResult where actId= ?  ALLOW FILTERING;");	 
		      
		      countByUuid = session.prepare(
		    		  "select *  from liferay.lms_learningactivityResult where uuid_= ?  ALLOW FILTERING;");	
		      
		      countAll =  session.prepare(
		    		  "select count(*)  from liferay.lms_learningactivityresult;");
		      countByuser =  session.prepare(
		    		  "select count(*)  from liferay.lms_learningactivityresult where userId=?  ALLOW FILTERING;");
		      countByac =   session.prepare(
		    		  "select count(*)  from liferay.lms_learningactivityresult where actId=?  ALLOW FILTERING;");		 
		      
		      
		      remove_Statement = session.prepare(
				      "delete  from liferay.lms_learningactivityResult where larid IN ?;");
		      
		      
		   }	
	

	   public static void connect(){

           // Create session to hosts
		   /*		   
		   if(cluster==null){	
			   
			   
           Cluster cluster = new Cluster.Builder().addContactPoints(String.valueOf("127.0.0.1")).build();
           Metadata metadata = cluster.getMetadata();

           Session session = cluster.connect();

           Metadata metadata = cluster.getMetadata();
           System.out.println(String.format("Connected to cluster '%s' on %s.", metadata.getClusterName(), metadata.getAllHosts()));
		   }
*/
		   
		   
		  if(cluster==null){
			  
			  
			  
			  
		      cluster = Cluster.builder().addContactPoint(node).build();
  	         

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
