Ext.define('MyApp.store.RoleTreeStore',{
	extend: 'Ext.data.TreeStore',
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            sorters: [{
                property: 'index',
                direction: 'ASC'
            }]
        }, cfg)]);
    }
});