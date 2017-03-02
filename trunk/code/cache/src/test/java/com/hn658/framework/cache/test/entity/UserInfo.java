package com.hn658.framework.cache.test.entity;

import com.hn658.framework.cache.base.CacheStorage;
import com.hn658.framework.cache.base.CacheType;
import com.hn658.framework.shared.entity.BaseEntity;

import java.util.Date;

/**
 * Created by baihai on 2015/10/26.
 */
@CacheStorage(type = CacheType.Local)
public class UserInfo extends BaseEntity{


    private String username;
    private String password;
    private Date createTime;

    public UserInfo(){
        super();
    }

    public UserInfo(Long id,String username,
                    String password,Date createTime){
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
