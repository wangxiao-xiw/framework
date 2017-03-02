Ext.define('MyApp.store.FunctionTreeStore',{
    extend: 'Ext.data.TreeStore',
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            proxy: {
                type: 'ajax',
                actionMethods: 'POST',
                url: './rs/function/queryAllChildFunctionById',
                reader: {
                    type: 'json',
                    root: 'nodes'
                }
            },
            nodeParam: 'id',
            root: {
                id: 100000,
                text: "系统权限管理",
                expanded: true
            }
        }, cfg)]);
    }
});