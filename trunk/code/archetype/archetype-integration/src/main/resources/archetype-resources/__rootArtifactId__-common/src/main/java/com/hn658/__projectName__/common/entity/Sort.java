package com.hn658.${projectName}.common.entity;

import java.io.Serializable;

import com.hn658.${projectName}.common.constants.*;


public class Sort implements Serializable {

	private static final long serialVersionUID = 7830457143339925385L;

	private String property;
	
	private String direction;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public boolean isAsc(){
		return this.direction.equals(${ConstantClassName}.ASC);
	}
	
}
