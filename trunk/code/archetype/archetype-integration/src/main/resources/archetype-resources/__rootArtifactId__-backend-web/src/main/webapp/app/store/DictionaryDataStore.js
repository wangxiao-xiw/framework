Ext.define('MyApp.store.DictionaryDataStore', {
	extend: 'Ext.data.Store',
	model: 'MyApp.model.DictionaryDataModel',
	proxy: {
		type: 'ajax',
		actionMethods: 'post',
		url: './rs/dictionary/queryDictionaryData',
		reader: {
			type: 'json',
			root: 'dictionaryDataList',
			totalProperty: 'totalCount'
		}
	}
});


