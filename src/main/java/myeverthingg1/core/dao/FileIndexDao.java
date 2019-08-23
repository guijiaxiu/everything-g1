package myeverthingg1.core.dao;

import myeverthingg1.core.model.Condition;
import myeverthingg1.core.model.Thing;

import java.util.List;

/**
 * 数据库访问的对象
 */
public interface FileIndexDao {
    // File ->Thing ->Database Table Record
    //CRUD ->c /R U D
    //插入
    void insert(Thing thing);
    //删除
    void delete(Thing thing);
    //查询
    List<Thing> query(Condition condition);
}
