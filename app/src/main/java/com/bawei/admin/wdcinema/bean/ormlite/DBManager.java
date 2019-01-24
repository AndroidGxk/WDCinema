package com.bawei.admin.wdcinema.bean.ormlite;

import android.content.Context;

import com.bawei.admin.wdcinema.bean.LoginBean;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

public class DBManager {
    private Dao<LoginBean, String> dao;

    public DBManager(Context context) throws SQLException {
        Helper helper = new Helper(context);
        dao = helper.getDao(LoginBean.class);
    }

    // 创建数据
    public void insertStudent(LoginBean student) throws SQLException {
        //在数据库中创建一条记录，作用与SQLiteDatabase.insert一样
        dao.createOrUpdate(student);
    }

    public void batchInsert(List<LoginBean> students) throws SQLException {
        dao.create(students);
    }

    /**
     * 查询数据
     *
     * @return
     * @throws SQLException
     */
    public List<LoginBean> getStudent() throws SQLException {
        List<LoginBean> list = dao.queryForAll();
        return list;
    }

    /**
     * 查询某个数据
     *
     * @return
     * @throws SQLException
     */
    public List<LoginBean> queryGuanyu() throws SQLException {
        //Eq是equals的缩写
        //方法1
        List<LoginBean> list = dao.queryForEq("nickName", "张飞");

        //方法2
//        QueryBuilder queryBuilder = dao.queryBuilder();
////        queryBuilder.offset(); //偏移量
////        queryBuilder.limit(8l); //最多几行  offset + limit 做分页
////        queryBuilder.orderBy("age",true);
//        queryBuilder.where().eq("nickName","关羽"); //多条件查询
//        List<UserInfoBean> query = queryBuilder.query();//此方法相当于build，提交设置
        return list;
    }

    /**
     * 删除数据
     *
     * @param student
     * @throws SQLException
     */
    public void deleteStudent(LoginBean student) throws SQLException {
        //只看id
        dao.delete(student);
    }

    /**
     * 删除数据
     *
     * @param student
     * @throws SQLException
     */
    public int deleteStudentAll(List<LoginBean> student) throws SQLException {
        //只看id
        int delete = dao.delete(student);
        return delete;
    }

    /**
     * 删除指定数据
     *
     * @throws SQLException
     */
    public void deleteGuanyu() throws SQLException {
        DeleteBuilder deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq("nickName", "关羽");
        deleteBuilder.delete();
    }

    /**
     * 修改数据
     *
     * @param student
     * @throws SQLException
     */
    public void updateStudent(LoginBean student) throws SQLException {
        student.setSessionId("关羽");
        dao.update(student);
    }


}
