
//@require @packageOverrides
Ext.Loader.setConfig({
	enabled : true
});

/*******************************************************************************
 * 当前登录用户
 */
var loginUser, systemManagerTreeNodes;

(function() {
	var queryCurrentInfo = function() {
		// Ajax请求当前登录用户信息
		Ext.Ajax.request({
			url : './rs/user/currentUserInfo',
			async : false,
			method : "GET",
			success : function(response, opts) {
				var result = Ext.decode(response.responseText);
				// 设置当前登录用户信息
				loginUser = result.userInfo;
				systemManagerTreeNodes = result.treeNodes;
			}
		});
	};
	queryCurrentInfo();
	
})();

Ext.define('CurrentUser', {
	singleton : true,
	
	//当前登录用户对象
	getCurrentUser : function() {
		if (!Ext.isEmpty(loginUser)) {
			return loginUser;
		}
	},
	//当前登录用户的用户名
	getRealName : function() {
		if (!Ext.isEmpty(loginUser)) {
			return CurrentUser.getCurrentUser().realName;
		}
	},
	//当前登录用户的用户名
	getLoginAccount : function() {
		if (!Ext.isEmpty(loginUser)) {
			return CurrentUser.getCurrentUser().loginAccount;
		}
	},
	//当前登录用户菜单
	getUserMenus: function(){
		if(!Ext.isEmpty(loginUser)){
			return Ext.Array.merge(systemManagerTreeNodes);
		}
	}
});

Ext.application({
	autoCreateViewport : true,
	name : 'MyApp'
});
Ext.QuickTips.init();