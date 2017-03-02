/*
 */
Ext.define('MyApp.store.RoleInfoStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.RoleInfoModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.RoleInfoModel',
            proxy: {
				type: 'ajax',
				actionMethods: 'POST',
				url: './rs/role/queryRoleList',
				reader: {
					type: 'json',
					root: 'roleInfoList',
					totalProperty : 'totalCount'
				}
			}
        }, cfg)]);
    }
});