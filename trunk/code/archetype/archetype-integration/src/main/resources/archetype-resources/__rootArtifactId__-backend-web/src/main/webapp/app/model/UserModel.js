/*
 * 用户信息
 */
Ext.define('MyApp.model.UserModel', {
    extend: 'Ext.data.Model',
    fields: [{
            name: 'id'//用户id
        },{
            name: 'userType'//用户类型 
        },{
            name: 'loginAccount'//用户登录名
        },{
            name: 'password'//登录密码
        },{
            name: 'realName'//真实姓名
        },{
            name: 'nickName'//昵称
        },{
            name: 'phoneNumber'//电话号码
        },{
            name: 'qq'//QQ
        },{
            name: 'weiXin'//微信
        },{
            name: 'sex'//性别
        },{
            name: 'avatarUrl'//头像地址
        },{
            name: 'sign'//个性签名
        },{
            name: 'lastUpdateTime',//最后更新时间
            type: 'date',
            convert: function(v, record){
            	if(!Ext.isEmpty(v)){
            		return new Date(v);
            	}else{
            		return null;
            	}
            }
        },{
            name: 'createTime',//创建时间
            type: 'date',
            convert: function(v, record){
            	if(!Ext.isEmpty(v)){
            		return new Date(v);
            	}else{
            		return null;
            	}
            }
        },{
			name: 'isDeleted',//是否启用
			type: 'boolean'
		}
    ]
});