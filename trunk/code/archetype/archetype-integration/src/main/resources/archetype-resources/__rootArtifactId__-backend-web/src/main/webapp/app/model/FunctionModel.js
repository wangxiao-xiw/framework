Ext.define('MyApp.model.FunctionModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'//
	}, {
		name : 'parentId' // 父功能
	}, {
		name : 'functionName'
	}, {
		name : 'uri'
	}, {
		name : 'functionLevel'
	}, {
		name : 'validFlag',
		type : 'boolean'
	}, {
		name : 'displayOrder'
	}, {
		name : 'leaf',
		type : 'boolean'
	}, {
		name : 'functionType'
	}, {
		name : 'functionDesc'
	}, {
		name : 'createTime',
		type : 'date',
		convert : function(v, record) {
			if (!Ext.isEmpty(v)) {
				return new Date(v);
			} else {
				return null;
			}
		}
	}, {
		name : 'lastUpdateTime',
		type : 'date',
		convert : function(v, record) {
			if (!Ext.isEmpty(v)) {
				return new Date(v);
			} else {
				return null;
			}
		}
	}, {
		name : 'isDeleted',// 是否启用
		type : 'boolean'
	} ]
});