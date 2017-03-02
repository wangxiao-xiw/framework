Ext.define('MyApp.model.DictionaryDataModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'id',
        type: 'int'
    }, {
        name: 'dictType',
        type: 'string'
    }, {
        name: 'valueName',
        type: 'string'
    }, {
        name: 'valueCode',
        type: 'string'
    }, {
        name : 'valueOrder',
        type : 'string'
    }, {
        name: 'language',
        type: 'string'
    }, {
        name: 'isDeleted',
        type: 'string'
    }, {
        name: 'noteInfo',
        type: 'string'
    },{
        name:'refCode',
        type:'string'
    }]
});