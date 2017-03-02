/**
 * 选中列表中的一行或多行移动至另一个列表中
 * @param grid 选中的列表
 * @param addStore 增加记录的store
 * @param removeStore 删除记录的store
 * @name select
 */
select = function(grid,addStore,removeStroe) {
	var _records = grid.getSelectionModel().getSelection();
	if (_records == null||_records.length<=0) {
		return false;
	}
	Ext.each(_records, function(item) {
		addStore.insert(0,item);
	});
	removeStroe.remove(_records);
	return true;
};

/**
 * 列表中的所有记录移动至另一个列表中
 * @param grid 选中的列表
 * @param addStore 增加记录的store
 * @param removeStore 删除记录的store
 * @name select
 */
allSelect = function(grid,addStore,removeStore) {
	var count = removeStore.getCount();
	if(count==null||count<=0){
		return false;
	}
	removeStore.each( function(_record) {
		addStore.insert(0,_record);
	},removeStore);
	// 从待选运单列表中移除该记录
	addStore.each( function(_record) {
		removeStore.remove(_record);
	},addStore);
	return true;
};



/**
 * 待分配角色选择列表
 */
Ext.define('MyApp.view.UserRoleChooseGrid', {
	extend : 'Ext.grid.Panel',
	frame: true,
	title : "待选择角色",
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	// 列模板
	columns : [{
		text : "角色名",
		flex: 1,
		dataIndex : 'roleName'
	}, {
		text : "角色详细信息",
		flex: 2,
		dataIndex : 'roleDesc'
	} ],
	queryField: null,
	getQueryField: function(){
		var me = this;
		if(me.queryField==null){
			me.queryField = Ext.create('Ext.form.field.Text');
		}
		return me.queryField;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create("Ext.selection.CheckboxModel");
		me.tbar = [me.getQueryField(),{
			text: "查询",
			handler: function(){
				var store = me.store,
					ameField = me.getQueryField(),
					roleName = ameField.getValue();
				store.clearFilter();
				if(!Ext.isEmpty(roleName)){
					store.filterBy(function(item) { 
						return item.get("roleName").indexOf(roleName) != -1; 
					});
				}
			}
		}];
		me.store = Ext.create('MyApp.store.RoleInfoStore',{
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : "./rs/user/queryUnAuthedRoles",
				reader: {
					type: 'json',
					root: 'roleInfoList'
				}
			}
		});
		me.callParent([cfg]);
	}
});

/**
 * 已分配角色选择列表
 */
Ext.define('MyApp.view.UserRoleGrid', {
	extend : 'Ext.grid.GridPanel',
	title : "已选择角色",
	frame: true,
    sortableColumns:false,
    enableColumnHide:false,
    enableColumnMove:false,
	stripeRows : true, // 交替行效果
	selType : "rowmodel", // 选择类型设置为：行选择
	// 列模板
	columns : [{
		text : "角色名",
		flex: 1,
		dataIndex : 'roleName'
	}, {
		text : "角色详细信息",
		flex: 2,
		dataIndex : 'roleDesc'
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.selModel = Ext.create("Ext.selection.CheckboxModel");
		me.store = Ext.create('MyApp.store.RoleInfoStore',{
			proxy : {
				type : 'ajax',
				actionMethods : 'POST',
				url : "./rs/user/queryAuthedRoles",
				reader: {
					type: 'json',
					root: 'roleInfoList'
				}
			}
		});
		me.callParent([cfg]);
	}
});

/**
 * 移动按钮
 */
Ext.define('MyApp.view.RoleButtonPanel', {   
	extend : 'Ext.panel.Panel',
	buttonAlign : 'center',
	defaults: {
		minWidth: 45,
		margin : '20 0 0 10',
		xtype: 'button'
	},
	items : [{
		text : '&gt;&gt;',
		margin : '60 0 0 10',
		handler :  function() {
			var window = this.up('window'),
				roleChooseGrid = window.getRoleChooseGrid(),
				noChoose_store = roleChooseGrid.getStore(),
				userRoleGrid = window.getUserRoleGrid(),
				choose_store = userRoleGrid.getStore();
			//将左边待选的应用为空或者供选择的数据数目小于或者等于零
			if(noChoose_store.getCount()==null || noChoose_store.getCount()<=0){
				Ext.ux.Toast.msg("温馨提示","左边应用已为空");
				return;
			}
			allSelect(roleChooseGrid,choose_store,noChoose_store);
		}
	}, {
		text : '&nbsp;&gt;&nbsp;',
		handler : function() {
			var window = this.up('window'),
				roleChooseGrid = window.getRoleChooseGrid(),
				noChoose_store = roleChooseGrid.getStore(),
				userRoleGrid = window.getUserRoleGrid(),
				choose_store = userRoleGrid.getStore();
			var _records = roleChooseGrid.getSelectionModel().getSelection();
			if (_records == null||_records.length<=0) {
				Ext.ux.Toast.msg("温馨提示","没有记录");
				return false;
			}
			select(roleChooseGrid,choose_store,noChoose_store);
		}
	}, {
		text : '&nbsp;&lt;&nbsp;',
		handler : function() {
			var window = this.up('window'),
				roleChooseGrid = window.getRoleChooseGrid(),
				noChoose_store = roleChooseGrid.getStore(),
				userRoleGrid = window.getUserRoleGrid(),
				choose_store = userRoleGrid.getStore();
			if(!select(userRoleGrid, noChoose_store, choose_store)){
				Ext.ux.Toast.msg("温馨提示","没有记录");
			}
		}
	}, {
		text : '&lt;&lt;',
		handler : function() {
			var window = this.up('window'),
				roleChooseGrid = window.getRoleChooseGrid(),
				noChoose_store = roleChooseGrid.getStore(),
				userRoleGrid = window.getUserRoleGrid(),
				choose_store = userRoleGrid.getStore();
			if(!allSelect(userRoleGrid, noChoose_store, choose_store)){
				Ext.ux.Toast.msg("温馨提示", "没有记录"); 
			}
		}
	}]
});

/**
 * 用户编辑窗口
 */
Ext.define('MyApp.view.UserRoleEditWindow',{
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	closeAction : 'hide',
	layout:'column',
	resizable:false,
	title : "用户角色管理",
    width:700,
	roleChooseGrid : null,
	getRoleChooseGrid : function(){
		if(this.roleChooseGrid==null){
			this.roleChooseGrid = Ext.create('MyApp.view.UserRoleChooseGrid',{
				columnWidth: .45,
				height:313
			});
		}
		return this.roleChooseGrid;
	},
	roleButtonPanel : null,
	getRoleButtonPanel : function(){
		if(this.roleButtonPanel==null){
			this.roleButtonPanel = Ext.create('MyApp.view.RoleButtonPanel',{
				columnWidth: .1,
				height:313
			});
		}
		return this.roleButtonPanel;
	},
	userRoleGrid : null,
	getUserRoleGrid : function(){
		if(this.userRoleGrid==null){
			this.userRoleGrid = Ext.create('MyApp.view.UserRoleGrid',{
				columnWidth: .45,
				height:313
			});
		}
		return this.userRoleGrid;
	},
	form: null,
	getForm: function(){
		var me = this;
		if(me.form==null){
			me.form = Ext.widget('form',{
				columnWidth: 1,
                layout: 'column',
				defaults: {
					margin: '15 5 15 5',
					columnWidth: .333,
					labelWidth: 85,
					xtype: 'textfield'
				},
                items: [{
					name: 'loginAccount',
					fieldLabel: '登录名(邮箱)'
				},{
					name: 'realName',
					fieldLabel: '真实姓名'
				},{
					name: 'phoneNumber',
					fieldLabel: '电话号码'
				}]
            });
		}
		return this.form;
	},
	bindData: function(record){
        var me = this,
            form = me.getForm().getForm(),
            roleChooseGrid = me.getRoleChooseGrid(),
            userRoleGrid = me.getUserRoleGrid();
        form.reset();
        form.loadRecord(record);
        //用户未分配的角色信息
        roleChooseGrid.getStore().load({
			params : {
				uid:record.get('id')	            				
			}
		});
		//用户已经分配的角色信息
        userRoleGrid.getStore().load({
			params : {
				uid:record.get('id')	            				
			}
		});
    },
    buttons: [{
		text:'保存',
		handler: function() {
			var me = this.up('window'),
				form = me.getForm().getForm(),
	            roleChooseGrid = me.getRoleChooseGrid(),
	            userRoleGrid = me.getUserRoleGrid();
				record = form.getRecord(),
				url = './rs/user/authUserRoles';
			if(!form.isValid()){
				return;
			}
			form.updateRecord(record);
			//将右边已选的应用构成一个数组
    		var chooseRoles = new Array();
    		chooseStore = userRoleGrid.getStore();
    		chooseStore.each( function(_role) {
    			chooseRoles.push(_role.get('id'));
    		},chooseStore);
			Ext.Ajax.request({
				url : url,
				jsonData: {'id': record.get('id'), 'roleIds': chooseRoles},
				success : function(response, opts) {
					Ext.ux.Toast.msg("温馨提示", "分配成功！");
					me.close();
				},
				exception : function(response, opts) {
					var json = Ext.decode(response.responseText);
					Ext.ux.Toast.msg("温馨提示", json.responseStatus.message);
				}
			});
		}
	}],
    constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.items = [ me.getForm(), me.getRoleChooseGrid(), me.getRoleButtonPanel(), me.getUserRoleGrid() ];
		me.callParent([cfg]);
	}
});
/*
 * 添加用户页面
 */

Ext.define('MyApp.view.userAvatarImage', {
	extend : 'Ext.Img',
	src: null,
	height: 64,
	width: 64,
	bindData : function(record, value, metadata, store, view) {
		var me = this;
		if(Ext.isEmpty(record.get('avatarUrl'))){
			return false;
		}else{
			me.setSrc(record.get('avatarUrl'));
			return true;			
		}
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([ cfg ]);
	}
});

/*
 * 用户管理页面
 */
Ext.define('MyApp.view.userManager', {
    extend: 'Ext.panel.Panel',
    id: 'userManager',
    closable: true,
    title: '管理用户信息',
	toolbar: null,
	getToolbar: function(){
		var me = this;
		if(Ext.isEmpty(me.toolbar)){
			me.toolbar = Ext.widget('toolbar',{
				dock: 'top',
				items: [{
                    xtype: 'button',
                    itemId: 'configUserRole',
                    text: '分配角色',
                    listeners: {
                        click: {
                            fn: me.configUserRole,
                            scope: me
                        }
                    }
                }]
			});
		}
		return me.toolbar;
	},
	userWindow: null,
	getUserWindow: function(){
		if(this.userWindow == null){
        	this.userWindow = new MyApp.view.userWindow();
        }
		return this.userWindow;
	},
    configUserAppWindow: null,
    getUserAppWindow: function(){
        if(this.configUserAppWindow == null){
            this.configUserAppWindow = new MyApp.view.UserAppEditWindow();
        }
        return this.configUserAppWindow;
    },
    //分配应用
    configUserApp:function(){
        var grid = this.getUserGrid(),
            selModel = grid.getSelectionModel(),
            selRecords = selModel.getSelection(),
            window = this.getUserAppWindow();
        if(selRecords == null||selRecords.length<=0){
            Ext.ux.Toast.msg("温馨提示", "请先选择用户");
            return;
        }
        window.bindData(selRecords[0]);
        window.show();
    },
    configUserRoleWindow: null,
    getUserRoleWindow: function(){
        if(this.configUserRoleWindow == null){
            this.configUserRoleWindow = new MyApp.view.UserRoleEditWindow();
        }
        return this.configUserRoleWindow;
    },
    //分配角色
    configUserRole:function(){
        var grid = this.getUserGrid(),
            selModel = grid.getSelectionModel(),
            selRecords = selModel.getSelection(),
            window = this.getUserRoleWindow();
        if(selRecords == null||selRecords.length<=0){
            Ext.ux.Toast.msg("温馨提示", "请先选择用户");
            return;
        }
        window.bindData(selRecords[0]);
        window.show();
    },
	queryForm: null,
	getQueryForm: function(){
		var me = this;
		if(me.queryForm==null){
			me.queryForm = Ext.widget('form',{
                layout: 'column',
				defaults: {
					margin: '10 10 10 10',
					columnWidth: .2,
					labelWidth: 80,
					xtype: 'textfield'
				},
                items: [{
					name: 'loginAccount',
					fieldLabel: '登录名'
				},{
					name: 'nickName',
					fieldLabel: '昵称'
				},{
					name: 'realName',
					fieldLabel: '姓名'
				}],
                buttons: [{
					text:'查询',
					handler: function() {
						me.getPagingToolbar().moveFirst();
					}
				},'-',{
					text:'重置',
					handler: function() {
						me.getQueryForm().getForm().reset();
					}
				},'->']
            });
		}
		return this.queryForm;
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
			me.store = Ext.create('MyApp.store.UserStore',{
				autoLoad: true,
				listeners: {
					beforeload : function(store, operation, eOpts) {
						var queryForm = me.getQueryForm();
						if (queryForm != null) {
							var values = queryForm.getValues();
							Ext.apply(operation, {
								params: {
									'loginAccount': Ext.String.trim(values.loginAccount),
									'nickName': Ext.String.trim(values.nickName),
									'realName': Ext.String.trim(values.realName)
								}
							});	
						}
					}
				}
			});
		}
		return me.store;
	},
	userGrid: null,
	getUserGrid: function(){
		var me = this;
		if(Ext.isEmpty(me.userGrid)){
			me.userGrid = Ext.widget('gridpanel',{
                header: false,
                columnLines: true,
				store: me.getStore(),
				columns: [{
					xtype: 'rownumberer'
				},{
					dataIndex: 'loginAccount',
					text: '登录名(邮箱)',
					xtype: 'tipcolumn', 
					tipConfig: {
						trackMouse: true,
					    hideDelay: 500 
					},
					align: 'center',
					tipBodyElement:'MyApp.view.userAvatarImage',
					flex: 1.5
				},{
					dataIndex: 'nickName',
					flex: 1,
					align: 'center',
					text: '昵称'
				},{
					dataIndex: 'realName',
					flex: 1,
					align: 'center',
					text: '姓名'
				},{
					dataIndex: 'sex',
					text: '性别',
					flex: 0.5,
					align: 'center',
					renderer: function(value){
						return DataDictionary.rendererSubmitToDisplay(value,'sex');
					}
				},{
					dataIndex: 'phoneNumber',
					flex: 1.5,
					align: 'center',
					text: '电话号码'
				},{
					dataIndex: 'qq',
					flex: 1,
					align: 'center',
					text: 'QQ'
				},{
					dataIndex: 'weiXin',
					flex: 1,
					align: 'center',
					text: '微信'
				},{
					dataIndex: 'sign',
					text: '个性签名',
					align: 'center',
					flex: 2
				},{
					dataIndex: 'isDeleted',
					text: '是否启用',
					flex: 1,
					align: 'center',
					renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
		            	return CommonFunction.rendererEnable(value);
		            }
				}],
				dockedItems: [me.getToolbar(),me.getPagingToolbar()]
			});
		}
		return me.userGrid;
	},
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [me.getQueryForm(),me.getUserGrid()]
        });
        me.callParent(arguments);
    }
});


