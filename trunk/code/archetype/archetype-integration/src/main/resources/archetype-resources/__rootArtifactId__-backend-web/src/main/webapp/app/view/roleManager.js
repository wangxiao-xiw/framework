//定义权限树结构
Ext.define('MyApp.view.RoleTree', {
	extend:'Ext.tree.Panel',
	title: '权限选择树',
	animate: false,
	useArrows: true,
	rootVisible: true,
	height : 385,
	expandNodes: [],
	tempFunctionIds: [],
	bindData: function(record, checkable){
		var me = this;
		me.tempFunctionIds = [];
		me.checkable = checkable;
		Ext.Ajax.request({
			url : './rs/function/loadFunctionChooseAllTree',
			method: 'POST',
			jsonData: {
				'roleId': record.get('id'),
				'checkable': checkable
			},
			success : function(response, opts) {
				var result = Ext.decode(response.responseText);
				me.getStore().setRootNode(result.node);
			}
		});
	},
	getCheckedFunctionIds: function() {
		var checkedNodes = this.getChecked(),
			ids = [];
		for (var t = 0; t < checkedNodes.length; t++) {
			ids[t] = checkedNodes[t].data.id;
		}
		return ids;
	},
	expandPaths: function(pathArray) {
		var me = this;
		me.collapseAll();
		var path;
		for (var i = 0; i < pathArray.length; i++) {
			path = pathArray[i];
			me.expandPath(path, 'id', '/', function(success, lastNode){
				if(success){
					me.expandNodes.push(lastNode);
					me.selectPath(path);
				}
			});	    						
		}
	},
	/** 当所有子节点没有选中时候，取消选择父节点 */
	deSelectParentFunction : function(node) {
		var me = this,
			nodeId = node.get('id'),
			parentNode = node.parentNode,
			parentId = parentNode.get('id'),
			rootNode = me.store.getRootNode(),
			rootId = rootNode.get('id');
		//根节点ID
		if (nodeId == rootId)
			return;
		if (parentNode.hasChildNodes()) {
			for (var i = 0; i < parentNode.childNodes.length; i++) {
				var childNode = parentNode.childNodes[i];
				if (childNode.data.checked == true) {
					return;
				}
				
			}
		}
		if(parentId != rootId){
			parentNode.set("checked", false);		
		}

		this.deSelectParentFunction(parentNode);
	},
	//checked所有父结点
	checkParent: function(node,checked){
		var me = this, parentNode = node.parentNode,
			nodeId = node.get('id');
		if(parentNode){
			/*父节点没选中*/
			if(parentNode.data.checked==false){
				parentNode.set('checked',true);
			}
			me.checkParent(parentNode, checked);
		}
	},
	//checked所有子结点
	checkChild: function checkChild(node,checked){
		var me = this;
		node.expand();
        node.set({checked:checked});
        if(node.hasChildNodes()){
            node.eachChild(function(child) {
            	//父节点没选中
    			if(child.data.checked==true){
    				child.set('checked',true);
    			}
    			me.checkChild(child, checked);
            });
        }
		/*var me = this;
		node.eachChild(function(node, childNode){
			父节点没选中
			if(childNode.data.checked==true){
				childNode.set('checked',true);
			}
			me.checkChild(childNode, checked);
		});*/
	},
	checkChage : function(node, checked){
		var me = this, 
			functionId = node.data.id;
		parentNode = node.parentNode;
		if (checked == true) {
			me.tempFunctionIds.push(functionId);
			me.checkParent(node, true);
			me.checkChild(node, true);
			node.set('checked',true); 
		} else {
		    //去除用户取消的编码
			Ext.Array.remove(me.tempFunctionIds, functionId);
			node.set('checked',false);
			// 判断父节点下是否还有子节点是选中状态
			this.deSelectParentFunction(node);
			node.data.checked = false; 
			me.checkChild(node, false);
			return false;
		}
		
	},
	checkable: null,
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('MyApp.store.RoleTreeStore');
		// 监听鼠标事件
		me.listeners = {
			checkchange: me.checkChage
		};
		me.callParent([cfg]);
	}
});

// 编辑窗口上面角色详情
Ext.define('MyApp.view.RoleForm', {
	extend:'Ext.form.Panel',
	layout:'column',
	defaults: {
		xtype : 'textfield',
		margin:'5 5 5 5',
		labelWidth: 70,
		readOnly: true
	},
	setFormFieldsReadOnly: function(flag) {
		var form = this.getForm();
		form.findField('roleName').setReadOnly(flag);
		form.findField('roleDesc').setReadOnly(flag);
	},
	initComponent: function(){
		var me = this;
		me.items = [{
			name: 'roleName',
			fieldLabel: "角色名称",
			columnWidth: .3,
			allowBlank: false
		},{
			name: 'roleDesc',
			columnWidth: .7,
			fieldLabel: "角色描述"
		}];
		me.callParent();
	}
});

/*角色修改窗口*/
Ext.define('MyApp.view.RoleWindow',{
	extend: 'Ext.window.Window',
	title: '角色编辑',
	width: 600,
	height: 500,
	modal: true,
	closeAction: 'hide',
	roleForm: null,
	showEditButtons: function(flag) {
		if (flag) {
        	this.saveButton.show();
		} else {
        	this.saveButton.hide();
		}
	},
	actionType: 'add',
	bindData: function(record, actionType){
		var me = this,
			roleFormView = me.getRoleForm(), 
			roleForm = roleFormView.getForm(),
			functionTree = me.getRoleFunctionTree(),checkable;
		me.actionType = actionType;
		roleForm.loadRecord(record);
		if (actionType) {
            if(actionType=="add"){
            	roleForm.reset();
    			me.setTitle("新增角色信息");
    			me.showEditButtons(true);
    			roleFormView.setFormFieldsReadOnly(false);
    			checkable = true;
    		}
    		if(actionType=="update"){
    			me.setTitle("编辑角色信息");
    			me.showEditButtons(true);
    			roleFormView.setFormFieldsReadOnly(false);
    			checkable = true;
    		}
    		if(actionType=="detail"){
    			me.setTitle("角色信息详情");
    			me.showEditButtons(false);
    			roleFormView.setFormFieldsReadOnly(true);
    			checkable = false;
    		}
        }
		functionTree.bindData(record, checkable);
		me.showEditButtons(true);
	},
	getRoleForm: function(){
		if(this.roleForm==null){
			this.roleForm = Ext.create("MyApp.view.RoleForm");
		}
		return this.roleForm;
	},
	roleFunctionTree: null,
	getRoleFunctionTree: function(){
		var me = this;
		if(Ext.isEmpty(me.roleFunctionTree)){
			me.roleFunctionTree = Ext.create('MyApp.view.RoleTree');
		}
		return me.roleFunctionTree;
	},
	/** 新增角色，验证通过返回true */
	saveRole: function() {
		var me = this,
			form = me.getRoleForm().getForm(),
			roleManager = Ext.getCmp('roleManager'),
			roleGrid = roleManager.getRoleGrid();
		if (!form.isValid()) {
			return false;
		}
		var record = form.getRecord();
    	form.updateRecord(record);
		Ext.Ajax.request({
	        url : 'rs/role/saveRole',
	        jsonData: {
	        	"roleInfo": record.data, 
				"functionIds": me.getRoleFunctionTree().getCheckedFunctionIds()
	        },
	        success : function(response) {
	        	var json = Ext.decode(response.responseText); 
	        	Ext.ux.Toast.msg('温馨提示',json.responseStatus.message);
	        	me.close();
	        	// 刷新表格
	        	roleGrid.getPagingToolbar().moveFirst();
	        },
	        exception : function(response) {
	        	var json = Ext.decode(response.responseText); 
	        	Ext.ux.Toast.msg('温馨提示', json.responseStatus.message);
	        }
	    });
		
		return true;
	},
	updateRole: function() {
		var me = this,
			form = me.getRoleForm().getForm(),
			roleManager = Ext.getCmp('roleManager'),
			roleGrid = roleManager.getRoleGrid();
		if (!form.isValid()) {
			return false;
		}
		var record = form.getRecord();
		form.updateRecord(record);
		Ext.Ajax.request({
			url: 'rs/role/updateRole',
			jsonData: {
				'functionIds': me.getRoleFunctionTree().getCheckedFunctionIds(),
				"roleInfo": record.data
			},
			success : function(response) {
				var json = Ext.decode(response.responseText); 
	        	Ext.ux.Toast.msg('温馨提示',json.responseStatus.message);
	        	me.close();
	        	// 刷新表格
	        	roleGrid.getPagingToolbar().moveFirst();
	        },
	        exception : function(response) {
	        	var json = Ext.decode(response.responseText); 
	        	Ext.ux.Toast.msg('温馨提示', json.responseStatus.message);
	        }
		});
	},
	saveButton : null, // 保存按钮
	getSaveButton : function(){
		var me = this;
		if (Ext.isEmpty(me.saveButton)) {
			me.saveButton = Ext.create('Ext.button.Button', {
				text: '保存',
				handler: function() { // 点击保存
		            if(me.actionType=="add"){
						me.saveRole();
					} else {
						me.updateRole();
					}
				}
			});
		}
		return this.saveButton;
	},
	constructor: function(config){
		var me = this,
		cfg = Ext.apply({
			items: [
				me.getRoleForm(), me.getRoleFunctionTree()
		    ],
			buttons: [
				me.getSaveButton()
		 	]
		}, config);
		me.callParent([cfg]);
	}
});

/*角色查询结果Panel*/
Ext.define('MyApp.view.RoleGrid',{
	extend: 'Ext.grid.Panel',
	title: '角色列表',
	columnLines: true,
    updateRoleWindow: null,
	getUpdateRoleWindow: function(){
		var me = this;
		if(me.updateRoleWindow == null){
			me.updateRoleWindow = Ext.create('MyApp.view.RoleWindow');
		}
		return this.updateRoleWindow;
	},
	toolbar: null,
	getToolbar: function(){
		var me = this;
		if(Ext.isEmpty(me.toolbar)){
			me.toolbar = Ext.widget('toolbar',{
				dock: 'top',
				items: [{
					text: '新增',
					handler: function(){ // 新增按钮
						var roleWindow = me.getUpdateRoleWindow(),
							record = Ext.create('MyApp.model.RoleInfoModel');
						roleWindow.bindData(record, 'add');
						roleWindow.show();
					}
				}]
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
			me.store = Ext.create('MyApp.store.RoleInfoStore',{
	    		pageSize:20,
	    		autoLoad: true,
	    		listeners: {	
	    			beforeload : function(store, operation, eOpts) {
	    				var roleManager = Ext.getCmp("roleManager"),
	    				roleQueryForm = roleManager.getRoleQueryForm();
	    				if (roleQueryForm != null) {
	    					var roleNameLike = roleQueryForm.getForm().findField('roleNameLike').getValue();
	    					Ext.apply(operation, {
	    						params : {
	    							'roleNameLike' : roleNameLike
	    						}
	    					});	
	    				}
	    			}
	    		}
	    	})
		}
		return me.store;
	},
	initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
        	store: me.getStore(),
			dockedItems: [me.getToolbar(), me.getPagingToolbar()],
			columns: [{
				xtype:'rownumberer',
				width:40
			},{
	            xtype:'actioncolumn',
	            flex: 1,
				text: "操作",
				align: 'center',
	            items: [{
			        tooltip: ' 详情',
			        icon: './images/common/refresh_16.png',
			        handler: function(grid, rowIndex, colIndex, item, e, record, row) { // 查看
			        	var roleWindow = me.getUpdateRoleWindow();
						roleWindow.bindData(record, 'detail');
						roleWindow.show();
			        }
			    }, '-',{
			    	icon: './images/chat/handle_48.png',
			        tooltip: "修改",
			        handler: function(grid, rowIndex, colIndex, item, e, record, row) { // 修改按钮
			        	var roleWindow = me.getUpdateRoleWindow();
						roleWindow.bindData(record, 'update');
						roleWindow.show();
			        }
			    }, '-',{
	            	icon: './images/common/delete.png',
			        tooltip: "删除",
			        handler: function(grid, rowIndex, colIndex, item, e, record, row) {
			        	/*删除*/
			        	var params = {
				        	'id': record.get('id')
			        	};
						Ext.Msg.confirm('温馨提示', "是否删除角色信息",
							function(btn){
								if(btn=='yes'){
									Ext.Ajax.request({
				     		            url : './rs/role/deleteRole',
				     		            jsonData: params,
				     		            success : function(response) {
				     		            	var json = Ext.decode(response.responseText); 
				     		            	Ext.ux.Toast.msg('温馨提示', json.responseStatus.message, 'success');
											me.getPagingToolbar().moveFirst();
				     		            },
				     		            exception : function(response) {
				     		            	var json = Ext.decode(response.responseText); 
				     			        	Ext.ux.Toast.msg('温馨提示', json.responseStatus.message);
				     		            }
				     		        });
								}
							}
						);
			        }
			    }]
	        },{
				text : '角色名称',
				align: 'center',
				flex: 2,
				dataIndex : 'roleName'
			},{
				text: '角色描述',
				flex: 5,
				// 让表格可以自动换行
				xtype:'linebreakcolumn',
				dataIndex: 'roleDesc'
			}]
        });
        me.callParent(arguments);
    }
});

/* 角色查询条件 */
Ext.define('MyApp.view.RoleQueryForm',{
	extend:'Ext.form.Panel',
	layout : 'column',
	defaults: {
		xtype: 'textfield',
		margin: '5 5 5 5'
	},
	initComponent: function(){
		var me = this;
		me.items = [{
    		name: 'roleNameLike',
    		fieldLabel: "角色名称",
    		columnWidth: .3
    	}, {
    		xtype : 'button',
			text: '查询',
			width : 70,
			// 查询按钮的响应事件：
			handler: function() {
				var roleManager = Ext.getCmp("roleManager"),
					roleGrid = roleManager.getRoleGrid();
				roleGrid.getPagingToolbar().moveFirst();
			}
		}];
		me.callParent();
	}
});

Ext.define('MyApp.view.roleManager', {
    extend: 'Ext.panel.Panel',
    id: 'roleManager',
    closable: true,
    title: '角色管理',
    roleGrid:null,
    getRoleGrid:function(){
    	var me = this;
    	if(Ext.isEmpty(me.roleGrid)){
    		me.roleGrid = Ext.create('MyApp.view.RoleGrid');
    	}
    	return me.roleGrid;
    },
    roleQueryForm: null,
    getRoleQueryForm: function(){
    	var me = this;
    	if(Ext.isEmpty(me.roleQueryForm)){
    		me.roleQueryForm = Ext.create('MyApp.view.RoleQueryForm');
    	}
    	return me.roleQueryForm;
    },
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [me.getRoleQueryForm(), me.getRoleGrid()]
        });
        me.callParent(arguments);
    }
});