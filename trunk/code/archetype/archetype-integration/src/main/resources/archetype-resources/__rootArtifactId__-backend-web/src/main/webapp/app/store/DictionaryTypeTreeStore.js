/**
 * Created by baihai on 2015/7/31.
 */
Ext.define('MyApp.store.DictionaryTypeTreeStore',{
    extend: 'Ext.data.TreeStore',
    proxy: {
        type: 'ajax',
        actionMethods: 'POST',
        url: './rs/dictionary/queryDictionaryList',
        reader: {
            type: 'json',
            root: 'nodes'
        }
    },
    root: {
        id: '1000000',
        text: "root",
        expanded: true
    },
    nodeParam: 'code',
    sorters: [{
        property: 'text',
        direction: 'ASC'
    }]
});