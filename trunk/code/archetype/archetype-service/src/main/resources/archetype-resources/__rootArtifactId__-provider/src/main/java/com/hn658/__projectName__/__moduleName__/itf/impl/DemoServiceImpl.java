#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package com.hn658.${projectName}.${moduleName}.itf.impl;

import com.hn658.${projectName}.${moduleName}.itf.DemoService;

/**
 * @author davidcun
 */
public class DemoServiceImpl implements DemoService {

	@Override
	public String queryByName(String name) {
		
		return "DemoServiceImpl";
	}

}
