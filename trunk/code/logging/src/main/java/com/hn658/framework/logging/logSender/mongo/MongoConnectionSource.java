package com.hn658.framework.logging.logSender.mongo;

import java.net.UnknownHostException;

import ch.qos.logback.core.spi.ContextAwareBase;
import ch.qos.logback.core.spi.LifeCycle;

import com.hn658.framework.logging.model.LogInfoEO;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoConnectionSource extends ContextAwareBase implements LifeCycle {

	private boolean started;

	private String host = "127.0.0.1";
	
	private int port = 27017;

	private String dbName = "logInfoEO";
	
	private Mongo mongo;
	
	private DB db = null;  
    
	private DBCollection collection = null;  

	@Override
	public boolean isStarted() {
		return started;
	}

	@Override
	public void start() {
		try {  
			mongo = new Mongo(host,port);
        } catch (UnknownHostException e) {  
        	addError("WARNING: mongo db 配置信息有误.", e); 
        }  
        db = mongo.getDB(dbName);
        collection = db.getCollection(LogInfoEO.class.getSimpleName());
		started = true;
	}

	@Override
	public void stop() {
		started = false;
	}
	
	/**  
     * 自增Id  
     * @param idName 自增Id名称  
     * @return Id  
     */  
    public Integer getAutoIncreaseID(String idName) {  
        BasicDBObject query = new BasicDBObject();  
        query.put("name", idName);  
  
        BasicDBObject update = new BasicDBObject();  
        update.put("$inc", new BasicDBObject("id", 1));  
  
        DBObject dbObject2 = db.getCollection("inc_ids").findAndModify(query,  
                null, null, false, update, true, true);  
          
        Integer id = (Integer) dbObject2.get("id");  
        return id;  
    }

	public DBCollection getCollection() {
		return this.collection;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

}
