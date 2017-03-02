Ext.define('MyApp.store.FunctionStore', {
	extend : 'Ext.data.Store',
	requires : [ 'MyApp.model.FunctionModel' ],
	constructor : function(cfg) {
		var me = this;
		cfg = cfg || {};
		me.callParent([ Ext.apply({
			model : 'MyApp.model.FunctionModel',
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : './rs/function/queryFunctionList',
				reader : {
					type : 'json',
					root : 'functionList',
					totalProperty : 'totalCount'
				}
			}
		}, cfg) ]);
	}
});