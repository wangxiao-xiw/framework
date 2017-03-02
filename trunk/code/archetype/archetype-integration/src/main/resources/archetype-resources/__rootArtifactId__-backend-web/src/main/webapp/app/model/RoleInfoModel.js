// 角色Model
Ext.define('MyApp.model.RoleInfoModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id'
	}, {
		name : 'roleName',
		type : 'string'
	}, {
		name : 'roleDesc',
		type : 'string'
	}, {
		name : 'createTime',// 创建时间
		type : 'date',
		convert : function(v, record) {
			if (!Ext.isEmpty(v)) {
				return new Date(v);
			} else {
				return null;
			}
		}
	}, {
		name : 'lastUpdateTime',// 更新时间
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