package com.hn658.framework.dataaccess.solr;

import com.hn658.framework.dataaccess.pagination.GenericPage;
import com.hn658.framework.dataaccess.solr.dao.IUsersSolr;
import com.hn658.framework.dataaccess.solr.entity.Users;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.PartialUpdate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zdl on 15/11/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class })
@ContextConfiguration(locations = { "classpath:/META-INF/hn658-solr-test-context.xml" })
public class UsersSolrTest {

    @Autowired
    public IUsersSolr usersSolr;

    /*------------ 增加 ------------*/
    @Test
    public void saveDocumentTest(){
        Users users = new Users(Users.Prefix);
        users.setName("小明");
        users.setAge(20);
        users.setHeight(169.9f);
        users.setMoney(BigDecimal.valueOf(6000));
        users.setIsDeleted(false);
        users.setCreateTime(new Date());

        usersSolr.saveDocument(users);

        Users s = usersSolr.queryByIndex(users.getIndexId());
        Assert.assertEquals(users.getIndexId(),s.getIndexId());

        usersSolr.deleteByAll();
    }

    @Test
    public void saveDocumentListTest(){
        List<Users> list = new ArrayList<Users>();
        for(int i=0;i<3;i++){
            Users users = new Users(Users.Prefix);
            users.setName("小明"+i);
            users.setHeight(i+0F);
            users.setAge(i);
            users.setMoney(BigDecimal.valueOf(i));
            users.setIsDeleted(false);
            users.setCreateTime(new Date());

            list.add(users);
        }
        usersSolr.saveDocumentList(list);

        Users users = usersSolr.queryByIndex(list.get(0).getIndexId());
        Assert.assertEquals(users.getIndexId(),list.get(0).getIndexId());
        Users users1 = usersSolr.queryByIndex(list.get(1).getIndexId());
        Assert.assertEquals(users1.getIndexId(),list.get(1).getIndexId());
        Users users2 = usersSolr.queryByIndex(list.get(2).getIndexId());
        Assert.assertEquals(users2.getIndexId(),list.get(2).getIndexId());

        usersSolr.deleteByAll();
    }

    /*------------ 删除 -------------*/
    @Test
    public void deleteByIndexTest(){
        Users users = new Users(Users.Prefix);
        users.setName("小明");
        users.setAge(20);
        users.setHeight(169.9f);
        users.setMoney(BigDecimal.valueOf(6000));
        users.setIsDeleted(false);
        users.setCreateTime(new Date());
        usersSolr.saveDocument(users);

        usersSolr.deleteByIndex(users.getIndexId());

        Users users1 = usersSolr.queryByIndex(users.getIndexId());
        Assert.assertNull(users1);

        usersSolr.deleteByAll();
    }

    @Test
    public void deleteByIndexListTest(){
        List<Users> list = new ArrayList<Users>();
        for(int i=0;i<3;i++){
            Users users = new Users(Users.Prefix);
            users.setName("小明"+i);
            users.setHeight(i+0F);
            users.setAge(i);
            users.setMoney(BigDecimal.valueOf(i));
            users.setIsDeleted(false);
            users.setCreateTime(new Date());

            list.add(users);
        }
        usersSolr.saveDocumentList(list);

        List<String> strings = new ArrayList<String>();
        for(Users users: list){
            strings.add(users.getIndexId());
        }
        usersSolr.deleteByIndexList(strings);
        Assert.assertNull(usersSolr.queryByIndex(list.get(0).getIndexId()));
        Assert.assertNull(usersSolr.queryByIndex(list.get(1).getIndexId()));
        Assert.assertNull(usersSolr.queryByIndex(list.get(2).getIndexId()));
    }

    @Test
    public void deleteQueryTest(){
        List<Users> list = new ArrayList<Users>();
        for(int i=0;i<3;i++){
            Users users = new Users(Users.Prefix);
            users.setName("小明"+i);
            users.setHeight(i+0F);
            users.setAge(i);
            users.setMoney(BigDecimal.valueOf(i));
            users.setIsDeleted(false);
            users.setCreateTime(new Date());

            list.add(users);
        }
        usersSolr.saveDocumentList(list);

        List<SearchCriteria> searchCriterias = new ArrayList<SearchCriteria>();
        searchCriterias.add(new SearchCriteria(Users.AGE, SearchCriteria.LOGIC.lessThanEqual,1));
        usersSolr.deleteQuery(searchCriterias);
        Assert.assertNull(usersSolr.queryByIndex(list.get(0).getIndexId()));
        Assert.assertNull(usersSolr.queryByIndex(list.get(1).getIndexId()));

        searchCriterias.clear();
        searchCriterias.add(new SearchCriteria(Users.INDEX_TYPE, SearchCriteria.LOGIC.equal,Users.Prefix));
        usersSolr.deleteQuery(searchCriterias);
        Assert.assertNull(usersSolr.queryByIndex(list.get(2).getIndexId()));
    }

    /*------------ 查询 ---------------*/
    @Test
    public void queryByIndexTest(){
        Users users = new Users(Users.Prefix);
        users.setName("小明");
        users.setAge(20);
        users.setHeight(169.9f);
        users.setMoney(BigDecimal.valueOf(6000.0));
        users.setIsDeleted(false);
        users.setCreateTime(new Date());
        usersSolr.saveDocument(users);

        Users users1 = usersSolr.queryByIndex(users.getIndexId());
        Assert.assertEquals(users1.getIndexId(),users.getIndexId());
        Assert.assertEquals(users1.getAge(),users.getAge());
        Assert.assertEquals(users1.getCreateTime(),users.getCreateTime());
        Assert.assertEquals(users1.getHeight(),users.getHeight());
        Assert.assertEquals(users1.getIsDeleted(),users.getIsDeleted());
        Assert.assertEquals(users1.getMoney(),users.getMoney());
        Assert.assertEquals(users1.getIndexType(),users.getIndexType());

        usersSolr.deleteByAll();
    }

    @Test
    public void queryObjectTest(){
        Users users = new Users(Users.Prefix);
        users.setName("小明");
        users.setAge(20);
        users.setHeight(169.9f);
        users.setMoney(BigDecimal.valueOf(6000.0));
        users.setIsDeleted(false);
        users.setCreateTime(new Date());
        usersSolr.saveDocument(users);

        List<SearchCriteria> searchCriterias = new ArrayList<SearchCriteria>();
        searchCriterias.add(new SearchCriteria(Users.MONEY,SearchCriteria.LOGIC.greaterThan,500));
        Users u = usersSolr.queryObject(searchCriterias);
        Assert.assertEquals(users.getMoney(),u.getMoney());

        usersSolr.deleteByAll();
    }

    @Test
    public void queryPageTest(){
        List<Users> list = new ArrayList<Users>();
        for(int i=0;i<23;i++){
            Users users = new Users(Users.Prefix);
            users.setName("小明"+i);
            users.setHeight(i+0F);
            users.setAge(i);
            users.setMoney(BigDecimal.valueOf(i));
            users.setIsDeleted(false);
            users.setCreateTime(new Date());

            list.add(users);
        }
        usersSolr.saveDocumentList(list);
        List<SearchCriteria> searchCriterias = new Stack<SearchCriteria>();
        searchCriterias.add(new SearchCriteria(Users.INDEX_TYPE, SearchCriteria.LOGIC.equal,Users.Prefix));
        GenericPage<Users> usersGenericPage = usersSolr.queryPage(searchCriterias, 10, 9, null, false);
        List<Users> userses = usersGenericPage.getData();
        usersSolr.deleteByAll();
    }

    @Test
    public void partialUpdateTest(){
        Users users = new Users(Users.Prefix);
        users.setName("小明");
        users.setAge(20);
        users.setHeight(169.9f);
        users.setMoney(BigDecimal.valueOf(6000.0));
        users.setIsDeleted(false);
        users.setCreateTime(new Date());
        usersSolr.saveDocument(users);

        PartialUpdate partialUpdate = new PartialUpdate(Users.INDEX_ID,users.getIndexId());
        partialUpdate.add(Users.AGE,1234);
        partialUpdate.add(Users.NAME,null);
        usersSolr.partialUpdate(partialUpdate);
        Users users2 = usersSolr.queryByIndex(users.getIndexId());
        Assert.assertEquals(users2.getAge(),Integer.valueOf(1234));
        Assert.assertNull(users2.getName());

        usersSolr.deleteByAll();
    }

}

