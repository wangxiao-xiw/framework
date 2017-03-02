/*
 * 用户银行信息
 */
Ext.define('MyApp.model.DictionaryTypeModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'id',//用户id
        type: 'long'
    },{
        name: 'typeName'//
    },{
        name: 'typeAlias'//
    },{
        name: 'parentCode'//
    },{
        name:'isLeaf'
    },{
        name:'isDeleted'
    },{
        name: 'createDate',//
        type: 'date',
        convert: function(v, record){
        	if(!Ext.isEmpty(v)){
        		return new Date(v);
        	}else{
        		return null;
        	}
        }
    },{
        name: 'modifyDate',//
        type: 'date',
        convert: function(v, record){
        	if(!Ext.isEmpty(v)){
        		return new Date(v);
        	}else{
        		return null;
        	}
        }
    }]
});