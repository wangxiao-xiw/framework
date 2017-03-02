package com.hn658.framework.dataaccess.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author ztjie
 *
 */
public abstract class AbstractMongoDAO {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected MongoTemplate mongoTemplate;
	
	/**
	 * @return the template
	 */
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

	/**
	 * @param template the template to set
	 */
	@Autowired(required=false)
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
}
