//全局参数
Ext.define('MyApp.view.functionManager', {
    extend: 'Ext.panel.Panel',
    id: 'functionManager',
    closable: true,
    title: '系统权限管理',
    layout : 'border',
    functionTreePanel:null,
    getFunctionTreePanel:function(){
        var me = this;
        if(Ext.isEmpty(me.functionTreePanel)){
            me.functionTreePanel = Ext.create('MyApp.view.FunctionTreePanel', {
                width : 280,
                region:'west'
            });
        }
        return me.functionTreePanel;
    },
    functionGrid:null,
    getFunctionGrid:function(){
    	var me = this;
    	if(Ext.isEmpty(me.functionGrid)){
    		me.functionGrid = Ext.create('MyApp.view.FunctionGrid');
    	}
    	return me.functionGrid;
    },
    functionForm: null,
    getFunctionForm: function(){
    	var me = this;
    	if(Ext.isEmpty(me.functionForm)){
    		me.functionForm = Ext.create('MyApp.view.FunctionForm');
    	}
    	return me.functionForm;
    },
    centerPanel: null,
    getCenterPanel: function(){
    	var me = this;
    	if(Ext.isEmpty(me.centerPanel)){
    		me.centerPanel = Ext.create('Ext.panel.Panel',{
    			region:'center',
            	items: [me.getFunctionForm(), me.getFunctionGrid()]
    		});
    	}
    	return me.centerPanel;
    },
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [me.getFunctionTreePanel(), me.getCenterPanel()]
        });
        me.callParent(arguments);
    }
});

/**
 * 定义树面板
 */
Ext.define('MyApp.view.FunctionTreePanel', {
    extend: 'Ext.tree.Panel',
    title: "权限树",
    animate: true,
	folderSort: false,
	lines: false,
	rootVisible: true,
	useArrows: true,
    operationMenu: null,
    selectNodeRecord: null,
    initComponent: function() {
        var me = this;
        me.store = Ext.create('MyApp.store.FunctionTreeStore');
        me.on('itemclick', me.onClickNode, me);
        me.on('itemcontextmenu', me.onCreateMenu, me);
        me.callParent();
    },
    onClickNode: function(node, record, item, index, e, eOpts) {
        var me = this,
        	functionManager = Ext.getCmp('functionManager'),
        	functionGrid = functionManager.getFunctionGrid(),
        	functionForm = functionManager.getFunctionForm();
        me.selectNodeRecord = record;
        functionForm.loadFormDataFromTree(record);
        //不是叶子结点，展开下级子部门
        if(record.data.leaf != true) {
        	functionGrid.disabledButtons(true);
            return;
        //点击叶子结点加载右侧页面功能列表
        }else{
        	functionGrid.disabledButtons(false);
            functionGrid.getStore().loadPage(1);
        }
    },
    onCreateMenu: function(view, record, item, index, e, eOpts) {
        var me = this,
            operationMenu = me.createCellItem();
        e.preventDefault();
        if(Ext.isEmpty(record.get('parentId'))) {
            operationMenu.child('#modify').setVisible(false);
            operationMenu.child('#delete').setVisible(false);
        }else{
        	operationMenu.child('#modify').setVisible(true);
            operationMenu.child('#delete').setVisible(true);
        }
        operationMenu.showAt(e.getXY());
        me.selectNodeRecord = record;
    },
    createCellItem: function() {
        var me = this;
        if(me.operationMenu == null) {
            me.operationMenu = Ext.create('Ext.menu.Menu', {
                items: [{
                    itemId: 'add',
                    text: "增加",
                    handler: function(){
                    	me.onAddNode();
                    },
                    scope: me
                }, {
                    itemId: 'modify',
                    text: "修改",
                    handler: function(){
                    	me.onModifyNode();
                    },
                    scope: me
                }, {
                    itemId: 'delete',
                    text: "删除",
                    handler: function(){
                    	me.onDeleteNode();
                    },
                    scope: me
                }, {
                    itemId: 'reflush',
                    text: "刷新",
                    handler: function(){
                    	me.onReflushNode();
                    },
                    scope: me
                }]
            });
        }
        return me.operationMenu;
    },
    getSelectNodeRecord: function() {
        return this.selectNodeRecord;
    },
    getFunctionWin: function(title, actionType) {
        var me = this;
        if (!me.functionWin) {
            me.functionWin = Ext.create('MyApp.view.FunctionWin');
        }
        return me.functionWin;
    },
    showAddNodeWin: function(record) {
        var addNodeWin = this.getFunctionWin(),
        	record = Ext.create('MyApp.model.FunctionModel',{
	    		'parentId': record.get('id')
	    	});
        addNodeWin.bindData("add", "增加新功能信息", record);
        addNodeWin.show();
    },
    showModifyNodeWin:function(record){
    	var modifyNodeWin = this.getFunctionWin();
    	modifyNodeWin.bindData("modify", "修改"+record.get("functionName")+"基本信息", record);
    	modifyNodeWin.show();
    },
    onAddNode: function() {
        var me = this,
            record = me.getSelectNodeRecord();
        me.showAddNodeWin(record);
    },
    onModifyNode:function(record){
    	var me = this;
    	if(Ext.isEmpty(record)){
    		record = me.getSelectNodeRecord();
    		record = Ext.create('MyApp.model.FunctionModel',record.raw.entity);
    	}
    	me.showModifyNodeWin(record);
    },
    onDeleteNode: function(record) {
        var me = this;
        Ext.Msg.confirm("确认操作", "删除功能信息", function(btn) {
            if(btn == "yes") {
            	if(Ext.isEmpty(record)){
            		record = me.getSelectNodeRecord();    		
            	}
            	var id = record.get('id');
                Ext.Ajax.request({
                    url: './rs/function/deleteFunction',
                    actionMethods: 'POST',
                    jsonData: {
                        'id': id
                    },
                    success: function(response) {
                    	var functionManager = Ext.getCmp('functionManager'),
	                    	functionGrid = functionManager.getFunctionGrid();
                    	if(record.get('functionType')!=3){
                        	me.getStore().load();                    	
                        }else{
                        	functionGrid.getStore().load();                    	
                        }
                    },
                    exception : function(response) {
                        var json = Ext.decode(response.responseText);
                        Ext.ux.Toast.msg("温馨提示", json.responseStatus.message);
                    }
                });
            }
        });
    },
    /**
	 * 刷新树的节点
	 */
	onReflushNode : function() {
		var me =this, store = me.getStore(), record = me.getSelectNodeRecord();
		node = store.getNodeById(record.get('id')); 
		store.load({
			node : node
		});
		return;
	}
});

/**
 * 定义右上功能表单详情
 */
Ext.define('MyApp.view.FunctionForm', {
	extend : 'Ext.form.Panel',
	title: "功能详情",
	layout:'column',
    defaults: {
    	columnWidth:.5,
    	margin:'5 5 5 5',
    	readOnly: true,
    	xtype: 'textfield'
    },
	/**
	 * 从功能树中加载数据到form表单中
	 */
	loadFormDataFromTree : function(record) {
		var me = this,
			form = me.getForm(),
			functionType = form.findField('functionType');
		// 如果父节点为空，那么当前节点就是功能树的根节点，不可以做修改
		if (record.raw.entity == null) {
			return;
		}
		var functionModel = Ext.create('MyApp.model.FunctionModel', record.raw.entity);
		me.loadRecord(functionModel);
	},
	constructor : function(config) {
		var me = this;
    	Ext.applyIf(me, {
			items: [DataDictionary.getDataDictionaryCombo('functionType',{
				fieldLabel: '功能类型',
				readOnly: true,
				name: 'functionType'
			}),{
				name: 'functionName',
		        fieldLabel: "功能名称"
			},{
				name: 'uri',
		        fieldLabel: "功能URI"
			},{
				name: 'displayOrder',
				fieldLabel: "显示顺序"
			},{
				name: 'functionDesc',
		        fieldLabel: "功能描述"
			},{
				xtype: 'checkboxfield',
				name: 'validFlag',
				boxLabel: '是否有效'
			}]
		}, config);
		this.callParent([config]); 
	}
});

Ext.define('MyApp.view.FunctionGrid',{
	extend:'Ext.grid.Panel',
	title:'按钮功能管理',
	columnLines: true,
	disabledButtons: function(flag) {
		this.getAddButton().setDisabled(flag);
	},
	// 新增按钮
	addButton: null,
	getAddButton: function() {
		var me = this;
		if(Ext.isEmpty(me.addButton)){
			me.addButton = Ext.create('Ext.Button', {
				text : '新增',
				disabled: true,
				handler : function() {
					var functionManager = Ext.getCmp('functionManager'),
						functionTreePanel = functionManager.getFunctionTreePanel();
					functionTreePanel.onAddNode();
				}
			});
		}
		return this.addButton;
	},
	toolbar: null,
	getToolbar: function(){
		var me = this;
		if(Ext.isEmpty(me.toolbar)){
			me.toolbar = Ext.widget('toolbar',{
				dock: 'top',
				items: [me.getAddButton()]
			});
		}
		return me.toolbar;
	},
	pagingToolbar: null,
	getPagingToolbar: function(){
		var me = this;
		if(me.pagingToolbar==null){
			me.pagingToolbar = Ext.widget('pagingtoolbar',{
				store: me.getStore(),
				dock: 'bottom',
				displayInfo: true
			});
		}
		return me.pagingToolbar;
	},
	store: null,
	getStore: function(){
		var me = this;
		if(me.store==null){
			me.store = Ext.create('MyApp.store.FunctionStore',{
	    		pageSize:20,
	    		listeners:{
	    			beforeload: function(store, operation, eOpts) {
	    				var functionManager = Ext.getCmp('functionManager'),
	    					functionTreePanel = functionManager.getFunctionTreePanel(),
	    					parentId = functionTreePanel.getSelectNodeRecord().get('id');
						Ext.apply(operation, {
							params: {
								'parentId': parentId
							}
						});
					}
	    		}
	    	})
		}
		return me.store;
	},
	columns: [{
		xtype:'rownumberer',
		width:40
	},{
    	xtype:'actioncolumn',
    	align:'center',
    	text:'操作',
    	items:[{
    		icon: './images/chat/handle_48.png',
            tooltip: '修改',
            handler: function(grid, rowIndex, colIndex, item, e, record, row){
            	var functionManager = Ext.getCmp('functionManager'),
					functionTreePanel = functionManager.getFunctionTreePanel();
            	functionTreePanel.onModifyNode(record);
            }
    	},'-',{
    		icon: './images/chat/bankcard_delete.png',
            tooltip: '删除',
            handler: function(grid, rowIndex, colIndex, item, e, record, row){
            	var functionManager = Ext.getCmp('functionManager'),
					functionTreePanel = functionManager.getFunctionTreePanel();
            	functionTreePanel.onDeleteNode(record);
            }
    	}]
    },{
    	text:'功能名称',
    	dataIndex:'functionName',
    	flex:1
    },{
    	text:'功能URI',
    	dataIndex:'uri',
    	flex:2
    },{
    	text:'显示顺序',
    	dataIndex:'displayOrder',
    	flex:1
    },{
    	text:'功能描述',
    	dataIndex:'functionDesc',
    	flex:2
    },{
    	text:'是否有效',
    	dataIndex:'validFlag',
    	flex:1
    }],
    initComponent:function(){
    	var me = this;
    	Ext.applyIf(me, {
    		store: me.getStore(),
        	dockedItems: [me.getToolbar(), me.getPagingToolbar()]
        });
    	me.callParent();
    }
});

Ext.define('MyApp.view.FunctionWin', {
    extend: 'Ext.window.Window',
    width: 600,
    closeAction: 'hide',
    modal: true,
    layout: 'fit',
    functionForm: null,
    getFunctionForm: function(){
    	var me = this;
    	if(me.functionForm==null){
    		me.functionForm = Ext.create('MyApp.model.EditFunctionForm')
    	}
    	return me.functionForm;
    },
    initComponent: function() {
        var me = this;
        me.items = [me.getFunctionForm() ],
        me.buttons = [{
            text: "取消",
            handler: function() {
                me.close();
            }
        }, '->', {
            text: "保存",
            handler: function() {
            	me.onSaveRecord();
            }
        }];
        me.callParent();
    },
    operationUrl: null,
    setOperationUrl: function(actionType) {
		var me = this;
		if (actionType === 'add') {
			me.operationUrl = './rs/function/addFunction';
		} else if (actionType === 'modify') {
			me.operationUrl = './rs/function/modifyFunction';
		}
	},
    bindData: function(actionType, title, record){
    	var me = this,
    		functionForm = me.getFunctionForm().getForm();
    	me.setOperationUrl(actionType);
    	me.setTitle(title);
    	functionForm.loadRecord(record);
        if (actionType) {
            if (actionType === "add") {
            	functionForm.reset();
            }
        }
    },
    onSaveRecord: function() {
        var me = this,
        	functionManager = Ext.getCmp('functionManager'),
        	functionTreePanel = functionManager.getFunctionTreePanel(),
        	functionGrid = functionManager.getFunctionGrid(),
	        functionForm = me.getFunctionForm().getForm(),
	        record = functionForm.getRecord();
        if (functionForm.isValid()) {
        	functionForm.updateRecord(record);
            Ext.Ajax.request({
                url: me.operationUrl,
                jsonData: {
                    'function': record.data
                },
                success: function(response) {
                    var json = Ext.JSON.decode(response.responseText);
                    if(record.get('functionType')!=3){
                    	functionTreePanel.getStore().load();                    	
                    }else{
                    	functionGrid.getStore().load();                    	
                    }
                    Ext.ux.Toast.msg("温馨提示", json.responseStatus.message);
                    me.hide();
                },
                exception : function(response) {
                    var json = Ext.decode(response.responseText);
                    Ext.ux.Toast.msg("温馨提示", json.responseStatus.message);
                }
            });
        }
    }
});

Ext.define('MyApp.model.EditFunctionForm', {
    extend: 'Ext.form.Panel',  
    layout: 'column',
    defaults: {
        margin: '5 5 5 5',
        columnWidth: .5,
        labelWidth: 80,
        xtype: 'textfield'
    },
    items: [DataDictionary.getDataDictionaryCombo('functionType',{
		fieldLabel: '功能类型',
		name: 'functionType',
		labelWidth: 80,
		editable: false
	}),{
        fieldLabel: "功能名称",
        name: 'functionName',
        allowBlank: false
    }, {
        fieldLabel: "功能URI",
        name: 'uri',
        allowBlank: false
    }, {
    	xtype: 'numberfield',
        fieldLabel: "显示顺序",
        name: 'displayOrder'
    }, {
        fieldLabel: "功能描述",
        name: 'functionDesc',
        allowBlank: false
    }, {
		xtype: 'checkboxfield',
		name: 'validFlag',
		boxLabel: '是否有效',
	    inputValue: true,
	    checked:true
	}],
    initComponent: function() {
        var me = this;
        me.callParent();
    }
});