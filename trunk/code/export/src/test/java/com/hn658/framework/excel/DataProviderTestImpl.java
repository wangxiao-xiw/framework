package com.hn658.framework.excel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hn658.framework.excel.domain.Student;
import com.hn658.framework.excel.export.AbstractAsyncDataProvider;

public class DataProviderTestImpl extends AbstractAsyncDataProvider {

	@Override
	public int getPageSize() {
		return 1;
	}

	@Override
	public int getTotalSize() {
		return 30;
	}

	@Override
	public List<Student> getData(int startRow, int endRow) {

		Student st = new Student("david", 21);
		st.setId("st");
		st.setDate(new Date());
		Student st1 = new Student("david1", 22);
		st1.setId("st1");
		st1.setDate(new Date());
		Student st2 = new Student("david2", 23);
		st2.setId("st2");
		st2.setDate(new Date());
		Student st3 = new Student("david3", 24);
		st3.setId("st3");
		st3.setDate(new Date());
		Student st4 = new Student("david4", 25);
		st4.setId("st4");
		st4.setDate(new Date());

		List<Student> sts = new ArrayList<Student>();

		sts.add(st);
		sts.add(st1);
		sts.add(st2);
		sts.add(st3);
		sts.add(st4);
		return sts;
	}

	@Override
	public List<Student> getData() {

		List<Student> sts = new ArrayList<Student>();

		Student st = new Student("david sync", 21);
		Student st1 = new Student("david1 sync", 22);
		Student st2 = new Student("david2 sync", 23);
		Student st3 = new Student("david3 sync", 24);
		Student st4 = new Student("david4 sync", 25);

		sts.add(st);
		sts.add(st1);
		sts.add(st2);
		sts.add(st3);
		sts.add(st4);
		
		return sts;

	}
	
	@Override
	public List<?> getData(Object param) {
		System.out.println("参数调用1");
		return this.getData();
	}
	
	@Override
	public List<?> getData(int startRow, int endRow, Object param) {
		System.out.println("参数调用3"+"---"+startRow+"---" +endRow);
		return this.getData(startRow, endRow);
	}

}
