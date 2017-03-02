package com.hn658.framework.scheduling.dao.jdbc;

public interface JDBCContants {
	//作业表
    String TABLE_JOB_SCHEDULE = "QRTZ_JOB_SCHEDULES";
    //作业日志表
    String TABLE_JOB_LOGGING = "QRTZ_JOB_LOGGINGS";
    //作业执行表
    String TABLE_JOB_PLANNING = "QRTZ_JOB_PLANNINGS";

    String COL_INSTANCE_ID = "INSTANCE_ID";
    //作业类型
    String COL_SCOPE_TYPE = "SCOPE_TYPE";
    //作业名称
    String COL_SCOPE_NAME = "SCOPE_NAME";
    //
    String COL_ACCESS_RULE = "ACCESS_RULE";
    
    String COL_ID = "ID";
    String COL_DESCRIPTION = "DESCRIPTION";
    //触发器状态
    String COL_TRIGGER_STATE = "TRIGGER_STATE";
    //触发器类型
    String COL_TRIGGER_TYPE = "TRIGGER_TYPE";
    //触发器表达式
    String COL_TRIGGER_EXPRESSION = "TRIGGER_EXPRESSION";
    //job实现类的完全包名
    String COL_JOB_CLASS = "JOB_CLASS";
    //一个blob字段，存放持久化job对象
    String COL_JOB_DATA = "JOB_DATA";
    //String COL_CLUSTER_NAME = "CLUSTER_NAME";
    String COL_INSTANCE_NAME = "INSTANCE_NAME";
    //触发器名称，外键
    String COL_TRIGGER_NAME = "TRIGGER_NAME";
    //所属触发器分组，外键
    String COL_TRIGGER_GROUP = "TRIGGER_GROUP";
    //作业名称
    String COL_JOB_NAME = "JOB_NAME";
    //所属分组的名称
    String COL_JOB_GROUP = "JOB_GROUP";
    //
    String COL_JOB_ACTION = "JOB_ACTION";
    String COL_FIRED_TIME = "FIRED_TIME";
    String COL_ERROR_MESSAGE = "ERROR_MESSAGE";
    String COL_FLOW_UUID = "FLOW_UUID";
    
    int TRIGGER_TYPE_SIMPLE = 0;
    int TRIGGER_TYPE_CRON = 1;
    
   //插入作业日志表SQL
   String INSERT_JOB_LOGGING = "INSERT INTO " + TABLE_JOB_LOGGING
                +"("
                    + COL_ID + ", "
                    + COL_INSTANCE_ID + ", "
                    + COL_TRIGGER_GROUP + ", "
                    + COL_TRIGGER_NAME + ", "
                    + COL_JOB_GROUP + ", "
                    + COL_JOB_NAME + ", "
                    + COL_FIRED_TIME + ", "
                    + COL_JOB_ACTION + ", "
                    + COL_FLOW_UUID + ", "
                    + COL_ERROR_MESSAGE
                + ") VALUES (?,?,?,?,?,?,?,?,?,?)";
   
   //插入作业的SQL
   String INSRET_JOB_SCHEDULE = "INSERT INTO " + TABLE_JOB_SCHEDULE
                   + "("
                        + COL_ID + ", "
                        + COL_TRIGGER_GROUP + ", "
                        + COL_TRIGGER_NAME + ", "
                        + COL_JOB_GROUP + ", "
                        + COL_JOB_NAME + ", "
                        + COL_DESCRIPTION + ", "
                        + COL_TRIGGER_TYPE + ", "
                        + COL_TRIGGER_EXPRESSION + ", "
                        + COL_JOB_CLASS + ", "
                        + COL_JOB_DATA                  
                   + ") VALUES (?,?,?,?,?,?,?,?,?,?)";
   
   //通过COL_INSTANCE_ID和COL_JOB_NAME查询作业日志
   String QUERY_LOGGING_BY_JOB = "SELECT "
                   + COL_INSTANCE_ID + ", "
                   + COL_TRIGGER_GROUP + ", "
                   + COL_TRIGGER_NAME + ", "
                   + COL_JOB_GROUP + ", "
                   + COL_JOB_NAME + ", "
                   + COL_FIRED_TIME + ", "
                   + COL_JOB_ACTION + ", "
                   + COL_FLOW_UUID + ", "
                   + COL_ERROR_MESSAGE
               + " FROM "
                   + TABLE_JOB_LOGGING
               + " WHERE "
                   + COL_INSTANCE_ID + "= ?"
               + " AND "  + COL_JOB_NAME + "= ?";
   
   //通过COL_INSTANCE_ID和COL_JOB_NAME查询触发器日志
   String QUERY_LOGGING_BY_TRIGGER = "SELECT "
                   + COL_INSTANCE_ID + ", "
                   + COL_TRIGGER_GROUP + ", "
                   + COL_TRIGGER_NAME + ", "
                   + COL_JOB_GROUP + ", "
                   + COL_JOB_NAME + ", "
                   + COL_FIRED_TIME + ", "
                   + COL_JOB_ACTION + ", "
                   + COL_FLOW_UUID + ", "
                   + COL_ERROR_MESSAGE
               + " FROM "
                   + TABLE_JOB_LOGGING
               + " WHERE "
                   + COL_INSTANCE_ID + "= ?"
               + " AND "  + COL_TRIGGER_NAME + "= ?";
   
   //通过COL_ID修改作业
   String UPDATE_SCHEDULE_BY_PK = "UPDATE "
                   + TABLE_JOB_SCHEDULE
                   + " SET "
                       + COL_TRIGGER_GROUP + " = ? ,"
                       + COL_TRIGGER_NAME + " = ? ,"
                       + COL_JOB_GROUP + " = ? ,"
                       + COL_JOB_NAME + " = ? ,"
                       + COL_DESCRIPTION + " = ? ,"
                       + COL_TRIGGER_TYPE + " = ? ,"
                       + COL_TRIGGER_EXPRESSION  + " = ? ,"
                       + COL_JOB_CLASS  + " = ? ,"
                       + COL_JOB_DATA + " = ? "
                   + " WHERE "
                       + COL_ID + " = ?";
   
   //查询作业集群
   String QUERY_SCHEDULE_BY_CLUSTER = "SELECT "
                   + COL_ID + ", "
                   + COL_TRIGGER_GROUP + ", "
                   + COL_TRIGGER_NAME + ", "
                   + COL_JOB_GROUP + ", "
                   + COL_JOB_NAME + ", "
                   + COL_DESCRIPTION + ", "
                   + COL_TRIGGER_TYPE + ", "
                   + COL_TRIGGER_EXPRESSION + ", "
                   + COL_JOB_CLASS + ", "
                   + COL_JOB_DATA
               + " FROM "
                   + TABLE_JOB_SCHEDULE
               /*+ " WHERE "
                   + COL_CLUSTER_NAME + "= ?"*/;
   //通过COL_TRIGGER_GROUP和COL_TRIGGER_NAME查询作业
   String QUERY_SCHEDULE_BY_TRIGGER = "SELECT "
                   + COL_ID + ", "
                   + COL_TRIGGER_GROUP + ", "
                   + COL_TRIGGER_NAME + ", "
                   + COL_JOB_GROUP + ", "
                   + COL_JOB_NAME + ", "
                   + COL_DESCRIPTION + ", "
                   + COL_TRIGGER_TYPE + ", "
                   + COL_TRIGGER_EXPRESSION + ", "
                   + COL_JOB_CLASS + ", "
                   + COL_JOB_DATA
               + " FROM "
                   + TABLE_JOB_SCHEDULE
               + " WHERE "
               		+ COL_JOB_GROUP + " = ? AND "
               		+ COL_JOB_NAME + " = ?";
   
   //通过COL_JOB_GROUP和COL_JOB_NAME删除作业
   String DELETE_SCHEDLE_BY_JOB = "DELETE "
               + " FROM "
                   + TABLE_JOB_SCHEDULE
               + " WHERE "
                   + COL_JOB_GROUP + " = ? AND "
                   + COL_JOB_NAME + " = ?";
   
   //通过COL_TRIGGER_GROUP和COL_TRIGGER_NAME删除作业
   String DELETE_SCHEDLE_BY_TRIGGER = "DELETE "
                   + " FROM "
                       + TABLE_JOB_SCHEDULE
                   + " WHERE "
                       + COL_TRIGGER_GROUP + " = ? AND "
                       + COL_TRIGGER_NAME + " = ?";

   //通过COL_INSTANCE_ID查询集群作业计划
   String QUERY_PLANNING_BY_CLUSTER = "SELECT "
                   + COL_INSTANCE_ID + ", "
                   + COL_SCOPE_TYPE + ", "
                   + COL_SCOPE_NAME + ", "
                   + COL_ACCESS_RULE
               + " FROM "
                   + TABLE_JOB_PLANNING
               + " WHERE "
                   + COL_INSTANCE_ID + " = ?";

}


