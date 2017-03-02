package com.hn658.${projectName}.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Map工具
 * @author angelXing
 *
 */
public class MapEntryUtils {
	
	/**
	 * 求交集(相同key, value)
	 * @param map1
	 * @param map2
	 * @return
	 */
	public static Map<Object, Object> intersect(Map<Object, Object> map1, Map<Object, Object> map2){
		Map<Object, Object> map=new HashMap<Object, Object>();
		Set<Entry<Object, Object>>set1=map1.entrySet();
		Set<Entry<Object, Object>>set2=map2.entrySet();
		
		List<Entry<Object, Object>>list2=new ArrayList<Entry<Object, Object>>();
		Iterator<Entry<Object, Object>> itSet2=set2.iterator();
		while(itSet2.hasNext()){
			list2.add(itSet2.next());
		}
		
		Iterator<Entry<Object, Object>> itSet1=set1.iterator();
		while(itSet1.hasNext()){
			Entry<Object, Object> entry1=itSet1.next();
			for(Entry<Object, Object> entry2:list2){
				if(entry1.equals(entry2)){
					map.put(entry1.getKey(), entry1.getValue());
				}
			}
		}
		return map;
	}
}
