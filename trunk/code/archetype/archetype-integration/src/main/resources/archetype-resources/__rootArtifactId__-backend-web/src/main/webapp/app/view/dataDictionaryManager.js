/**
 * Created by baihai on 2015/7/30.
 */

// 数据字典类型变量
dictType = null;
dictId = null;

Ext.define('MyApp.view.dataDictionaryManager', {
    extend: 'Ext.panel.Panel',
    id: 'dataDictionaryManager',
    closable: true,
    title: '数据字典',
//    cls : "panelContentNToolbar",
//    bodyCls : 'panelContentNToolbar-body',
    layout : 'border',
    dictionaryTypeTreePanel:null,
    getDictionaryTypeTreePanel:function(){
        var me = this;
        if(Ext.isEmpty(me.dictionaryTypeTreePanel)){
            me.dictionaryTypeTreePanel = Ext.create('MyApp.view.DictionaryTypeTreePanel', {
                id: 'MyApp.view.DictionaryTypeTreePanel_ID',
                width : 280,
                region:'west'
            });
            return me.dictionaryTypeTreePanel;
        }
    },
    dictionaryGrid:null,
    getDictionaryGrid:function(){
    	 var me = this;
         if(Ext.isEmpty(me.dictionaryGrid)){
             me.dictionaryGrid = Ext.create('MyApp.view.DictionaryGrid', {
                 id: 'MyApp.view.DictionaryGrid_ID',
                 region:'center'
             });
             return me.dictionaryGrid;
         }
    },
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [me.getDictionaryTypeTreePanel(),me.getDictionaryGrid()]
        });
        me.callParent(arguments);
    }
});


Ext.define('MyApp.view.DictionaryDataForm', {
	extend: 'Ext.form.Panel',
	defaultType: 'textfield',
	defaults: {
		margin: '5 5 5 5',
		labelWidth: 80,
		width: 320
	},
	initComponent: function() {
		var me = this;
		me.items = [/*{
            fieldLabel: '分类别名',
            name: 'dictType',
            allowBlank: false,
            readOnly: true
        },*/{
            fieldLabel: '关键字',
            name: 'valueCode',
            allowBlank: false,
            readOnly: true
        },{
			fieldLabel: '值',
			name: 'valueName',
			allowBlank: false
		},{
            fieldLabel: '序号',
            name: 'valueOrder',
            xtype: 'numberfield',
            allowBlank: false
        }, {
			fieldLabel: '备注信息',
			name: 'noteInfo',
			xtype: 'textarea'
		}];
		me.callParent();
	}
});


Ext.define('MyApp.view.DictionaryDataWin', {
	extend: 'Ext.window.Window',
	width: 380,
	height: 280,
	closable: true,
	resizable: false,
	closeAction: 'hide',
	modal: true,
	layout: 'fit',
	initComponent: function() {
		var me = this;
		me.items = [ Ext.create('MyApp.view.DictionaryDataForm') ],
		me.buttons = [{
			text: '取消',
			handler: function() {
				me.close();
			}
		}, '->', {
			text: '保存',
			cls:'yellow_button',
			handler: me.onSaveRecord,
			scope: me
		}],
		me.callParent();
	},
	dictionaryDataMode: null,
	getDictionaryDataModel: function() {
		if (null == this.dictionaryDataMode) {
			this.dictionaryDataMode = Ext.create("MyApp.model.DictionaryDataModel");
		}
		return this.dictionaryDataMode;
	},
	loadDictionaryData: function(record) {
		this.down('form').getForm().loadRecord(record);
		this.dictionaryDataMode = record;
	},
	operationUrl: null,
	setOperationUrl: function(url) {
		this.operationUrl = url;
	},
	onSaveRecord: function() {
		var me = this,
			dictionaryDataForm = me.down('form').getForm();
		if (dictionaryDataForm.isValid()) {
			dictionaryDataForm.updateRecord(me.getDictionaryDataModel());
            var url  = me.operationUrl.toString();
			Ext.Ajax.request({
				url:url,
				jsonData: {
					'dictionaryDataEntity': me.getDictionaryDataModel().data
				},
				success: function(response) {
					var josn = Ext.JSON.decode(response.responseText);
					if(Ext.isEmpty(josn.message)) {
						dictType = me.getDictionaryDataModel().data.dictType;
						Ext.getCmp('MyApp.view.DictionaryGrid_ID').store.load();
						Ext.ux.Toast.msg('提示', '保存成功');
						me.hide();
					} else {
						Ext.ux.Toast.msg('提示', josn.message, "error", 3000);
					}
				},
				exception : function(response) {
					var json = Ext.decode(response.responseText); 
					Ext.ux.Toast.msg(
							'提示', 
							json.message, 'error', 3000);
			    }
			});
		}
	}
});


Ext.define('MyApp.view.DictionaryGrid', {
	extend: 'Ext.grid.Panel',
	title: '数据项管理',
	frame: true,
    sortableColumns: false,
    enableColumnHide: false,
    enableColumnMove: false,
    height: 480,
    initComponent: function() {
    	var me = this;
    	me.columns = [
            { xtype: 'rownumberer', width: 40 }, 
            {
				xtype: 'actioncolumn',
				width: 60,
				align: 'center',
				text: '操作',
				items: [{
//					iconCls: 'deppon_icons_edit',
					icon: './images/chat/handle_48.png',
	                tooltip: '修改',
	                handler: me.onModifyRecord,
	                scope: me
				},'-', {
//					iconCls: 'deppon_icons_cancel',
					icon: './images/chat/bankcard_delete.png',
	                tooltip: '废除',
	                handler: me.onDeleteRecord
				}]
	    	}, 
	    	{ text: '关键字', dataIndex: 'valueCode', flex: 2 }, 
			{ text: '值', dataIndex: 'valueName', flex: 2 }, 
			{ text: '序号', dataIndex: 'valueOrder', flex: 1 }, 
			{ text: '备注信息', dataIndex: 'noteInfo', width: 180 }
		];
    	me.store = Ext.create('MyApp.store.DictionaryDataStore', {
			autoLoad: false,
			pageSize: 20,
			listeners: {
				beforeload: function(store, operation, eOpts) {
					Ext.apply(operation, {
						params: {
							'code': dictType
						}
					});
				}
			}
		});
    	me.tbar = [{
    		text: '新增',
    		handler: me.onAddRecord,
    		scope: me
    	}]
    	me.bbar = Ext.widget('pagingtoolbar',{
			store: me.getStore(),
			dock: 'bottom',
			displayInfo: true
		});
/*		me.bbar = Ext.create('Deppon.StandardPaging', {
			store: me.store
		})*/;
    	me.callParent();
    },
	getOperationUrl: function(actionType) {
		var operationUrl = null;
		if (actionType === 'add') {
			operationUrl = './rs/dictionary/addDictionaryData';
		} else if (actionType === 'update') {
			operationUrl = './rs/dictionary/modifyDictionaryData';
		}
		return operationUrl;
	},
	dictionaryDataWin: null,
	getDictionaryDataWin: function(title, actionType) {
		var me = this,
			dictionaryDataForm;
		if (!me.dictionaryDataWin) {
			me.dictionaryDataWin = Ext.create('MyApp.view.DictionaryDataWin');
		}
		dictionaryDataForm = me.dictionaryDataWin.down('form').getForm();
		me.dictionaryDataWin.setOperationUrl(me.getOperationUrl(actionType));
		me.dictionaryDataWin.setTitle(title);
		if (actionType) {
			if (actionType === "add") {
				dictionaryDataForm.findField('valueCode').setReadOnly(false);
				me.dictionaryDataWin.down('form').getForm().reset();
			} else if (actionType === "update") {
				dictionaryDataForm.findField('valueCode').setReadOnly(true);
			}
		}
		return me.dictionaryDataWin;
	},
    onAddRecord: function() {
    	var me = this,
    		tree = Ext.getCmp('MyApp.view.DictionaryTypeTreePanel_ID'),
    		record = tree.getSelectNodeRecord(),
    		dictWin;
    	if(record == null) {
    		Ext.Msg.alert('提示', '请先选择左侧数据字典分类！');
    		return;
    	}
    	if(record.data.leaf == false||dictType==null) {
    		Ext.Msg.alert('提示', '不能选择父级分类，请重新选择字典分类！');
    		return;
    	}
    	dictWin = me.getDictionaryDataWin('新增数据', "add");
    	dictWin.getDictionaryDataModel().set('dictType', record.raw.entity.typeAlias);
    	dictWin.show();
    },
    onModifyRecord: function(grid, rowIndex, colIndex) {
    	var me = this,
    		record = grid.store.getAt(rowIndex),
    		dictionaryWin;
    	dictionaryWin = me.getDictionaryDataWin('编辑数据', "update");
    	dictionaryWin.loadDictionaryData(record);
    	dictionaryWin.show();
    },
    onDeleteRecord: function(grid, rowIndex, colIndex) {
    	Ext.Msg.confirm('提示', '您确定要作废这笔数据吗？', function(btn) {
    		if(btn == "yes") {
    			var record = grid.store.getAt(rowIndex),
    				id = record.get('id'),
    				dictType = record.get('dictType');
    			Ext.Ajax.request({
    				url: './rs/dictionary/abolishDictionaryData',
    		        actionMethods: 'POST',
    				params: {
    					code: id//,
//    					dictType:dictType
    				},
    				success: function(response) {
    					/*grid.store.load({
							params: {
								code: dictType
							}
    					});*/
    					dictType = dictType;
						grid.store.load();
    				}
    			});
    		}
    	});
    }
});




Ext.define('MyApp.view.DictionaryTypeTreePanel', {
    extend: 'Ext.tree.Panel',
    height: 480,
    title: "数据字典分类",
    frame: true,
    autoScroll: true,
    useArrows: true,
    animate: true,
    rootVisible: true,
    operationMenu: null,
    selectNodeRecord: null,
    dictionaryTypeWin: null,
    initComponent: function() {
        var me = this;
        me.store = Ext.create('MyApp.store.DictionaryTypeTreeStore');
        me.listeners = {
            scrollershow: function(scroller) {
                if (scroller && scroller.scrollEl) {
                    scroller.clearManagedListeners();
                    scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller);
                }
            },
            itemclick: me.onClickNode
        };
        me.on('itemcontextmenu', me.onCreateMenu, me);
        me.callParent();
    },
    onClickNode: function(node, record, item, index, e) {
        var me = this,
            dictGrid = Ext.getCmp('MyApp.view.DictionaryGrid_ID');
        me.selectNodeRecord = record;
        if(record.data.leaf != true) {
            return;
        }

        dictId = record.data.id;
        dictType = record.raw.entity.typeAlias;
        dictGrid.store.loadPage(1);

    },
    getSelectNodeRecord: function() {
        return this.selectNodeRecord;
    },
    createCellItem: function() {
        var me = this;
        if(me.operationMenu == null) {
            me.operationMenu = Ext.create('Ext.menu.Menu', {
                items: [{
                    itemId: 'add',
                    text: "增加",
                    handler: me.onAddNode,
                    scope: me
                }, {
                    itemId: 'modify',
                    text: "修改",
                    handler: me.onModifyNode,
                    scope: me
                }, {
                    itemId: 'delete',
                    text: "删除",
                    handler: me.onDeleteNode,
                    scope: me
                }]
            });
        }
        return me.operationMenu;
    },
    onCreateMenu: function(view, record, item, index, e) {
        var me = this,
            operationMenu = me.createCellItem();
        e.preventDefault();
        if(record.raw) {
            if(record.raw.leaf == true) {
                operationMenu.child('#modify').setVisible(true);
                operationMenu.child('#delete').setVisible(true);
            } else {
                operationMenu.child('#modify').setVisible(true);
                operationMenu.child('#delete').setVisible(false);
            }
        } else{
            operationMenu.child('#modify').setVisible(false);
            operationMenu.child('#delete').setVisible(false);
        }
        operationMenu.showAt(e.getXY());
        me.selectNodeRecord = record;
    },
    getOperationUrl: function(actionType) {
        var operationUrl = null;
        if (actionType === 'add') {
            operationUrl = './rs/dictionary/addDictionaryType';
        } else if (actionType === 'update') {
            operationUrl = './rs/dictionary/modifyDictionaryType';
        }
        return operationUrl;
    },
    getDictionaryTypeWin: function(title, actionType) {
        var me = this,
            dictionaryTypeForm;
        if (!me.dictionaryTypeWin) {
            me.dictionaryTypeWin = Ext.create('MyApp.view.DictionaryTypeWin');
        }
        dictionaryTypeForm = me.dictionaryTypeWin.down('form').getForm()
        me.dictionaryTypeWin.setOperationUrl(me.getOperationUrl(actionType));
        me.dictionaryTypeWin.setTitle(title);
        if (actionType) {
            if (actionType === "add") {
                dictionaryTypeForm.findField('typeAlias').setReadOnly(false);
                me.dictionaryTypeWin.down('form').getForm().reset();
            } else if (actionType === "update") {
                dictionaryTypeForm.findField('typeAlias').setReadOnly(true);
            }
        }
        return me.dictionaryTypeWin;
    },
    showAddNodeWin: function(record) {
        typeWin = this.getDictionaryTypeWin("增加新类型", "add");
        typeWin.getDictionaryTypeModel().set('parentCode', record.data.id);
        typeWin.show();
    },
    onAddNode: function() {
        var me = this,
            record = me.getSelectNodeRecord(),
            typeWin;
        if(!Ext.isEmpty(record.raw)) {
            Ext.Ajax.request({
                url: './rs/dictionary/addConsumeBond',
                actionMethods: 'POST',
                jsonData: {
                    type: record.raw.typeAlias
                },
                success: function(response) {
                    var json = Ext.decode(response.responseText);
                    if(json.hasData) {
                        Ext.Msg.confirm("确认添加!", "此分类下包含数据，如果新增分类则可能会丢失相应数据，是否确定新增？", function(btn) {
                            if(btn == "yes") {
                                me.showAddNodeWin(record);
                            }
                        });
                    } else {
                        me.showAddNodeWin(record);
                    }
                },
                exception : function(response) {
                    var json = Ext.decode(response.responseText);
                    Ext.ux.Toast.msg("操作失败!");
                }
            });
            return;
        }
        me.showAddNodeWin(record);
    },
    onModifyNode: function() {
        var me = this,
            record = me.getSelectNodeRecord(),
            formModel, dictWin;
        if(Ext.isEmpty(record.raw)) {
            return;
        }
        formModel = Ext.create('MyApp.model.DictionaryTypeModel', record.raw.entity);
        dictWin = me.getDictionaryTypeWin("修改分类", "update");
        dictWin.loadDictionaryType(formModel);
        dictWin.show();
    },
    onDeleteNode: function() {
        var me = this;
        Ext.Msg.confirm("确认操作", "删除数据字典分类", function(btn) {
            if(btn == "yes") {
                var record = me.getSelectNodeRecord();
                id = record.get('id');
                Ext.Ajax.request({
                    url: './rs/dictionary/abolishDictionaryType',
                    actionMethods: 'POST',
                    jsonData: {
                        dictionaryTypeEntity:{
                            'id':id,
                            'parentCode':record.get('parentId')
                        }
                    },
                    success: function(response) {
                        Ext.getCmp('MyApp.view.DictionaryTypeTreePanel_ID').store.load();
                        dictType = id;
                        Ext.getCmp('MyApp.view.DictionaryGrid_ID').store.load();
                    }
                });
            }
        });
    }
});


Ext.define('MyApp.view.DictionaryTypeWin', {
    extend: 'Ext.window.Window',
    width: 380,
    height: 170,
    closable: true,
    resizable: false,
    closeAction: 'hide',
    modal: true,
    layout: 'fit',
    operationUrl: null,
    dictionaryTypeMode: null,
    initComponent: function() {
        var me = this;
        me.items = [ Ext.create('MyApp.model.DictionaryTypeForm') ],
            me.buttons = [{
                text: "取消",
                handler: function() {
                    me.close();
                }
            }, '->', {
                text: "保存",
                cls:'yellow_button',
                handler: me.onSaveRecord,
                scope: me
            }]
        me.callParent();
    },
    getDictionaryTypeModel: function() {
        if (null == this.dictionaryTypeMode) {
            this.dictionaryTypeMode = Ext.create("MyApp.model.DictionaryTypeModel");
        }
        return this.dictionaryTypeMode;
    },
    loadDictionaryType: function(record) {
        this.down('form').getForm().loadRecord(record);
        this.dictionaryTypeMode = record;
    },
    setOperationUrl: function(url) {
        this.operationUrl = url;
    },
    onSaveRecord: function() {
        var me = this,
            dictionaryTypeForm = me.down('form').getForm();
        if (dictionaryTypeForm.isValid()) {
            dictionaryTypeForm.updateRecord(me.getDictionaryTypeModel());
            Ext.Ajax.request({
                url: me.operationUrl.toString(),
                jsonData: {
                    'dictionaryTypeEntity':  me.getDictionaryTypeModel().data
                },
                success: function(response) {
                    var josn = Ext.JSON.decode(response.responseText);
                    if(Ext.isEmpty(josn.message)) {
                        Ext.getCmp('MyApp.view.DictionaryTypeTreePanel_ID').store.load();
                        Ext.ux.Toast.msg("操作成功!");
                        me.hide();
                    } else {
                        Ext.ux.Toast.msg("操作失败!");
                    }
                },
                exception : function(response) {
                    var json = Ext.decode(response.responseText);
                    Ext.ux.Toast.msg("操作异常!");
                }
            });
        }
    }
});


Ext.define('MyApp.model.DictionaryTypeForm', {
    extend: 'Ext.form.Panel',
    defaultType: 'textfield',
    defaults: {
    	margin: '5 5 5 5',
        labelWidth: 80,
        width: 320
    },
    initComponent: function() {
        var me = this;
        me.items = [{
            fieldLabel: "分类名称",
            name: 'typeName',
            allowBlank: false
        }, {
            fieldLabel: "分类别名",
            name: 'typeAlias',
            allowBlank: false,
            readOnly: true
        }];
        me.callParent();
    }
});
