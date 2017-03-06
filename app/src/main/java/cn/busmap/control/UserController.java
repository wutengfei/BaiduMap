package cn.busmap.control;

import android.content.Context;

import cn.busmap.model.DBAdapter;
import cn.busmap.model.User;


/**
 * Created by dell on 2016/9/18.
 */
public class UserController {
    private static DBAdapter dbAdapter;
    Context context;

    public UserController(Context context) {
        this.context = context;
        dbAdapter = new DBAdapter(context);
        dbAdapter.open();
    }

    //添加用户
    public boolean addUser(User user) {
        dbAdapter.insert(user);
        dbAdapter.close();
        return true;
    }

    //删除单个用户
    public boolean deleteUserByName(String username) {
        User user[] = dbAdapter.getOneByName(username);
        if (user != null) {
            dbAdapter.deleteOneDataByName(username);
            dbAdapter.close();
            return true;
        }
        return false;
    }

    //删除所有用户
    public void deleteAll() {
        dbAdapter.deleteAllData();
    }

    //修改密码
    public boolean updatePassword(User user) {
        String username = user.getUsername();
        //先查找到该用户
        User users[] = dbAdapter.getOneByName(username);
        if (users != null) {
            //更新密码
            dbAdapter.updateOneDataByName(username, user);
            dbAdapter.close();
        }
        return true;
    }

    //查询用户
    public User[] QueryOneByName(String username) {
        User[] user = dbAdapter.getOneByName(username);
        return user;
    }

    //查询所有图书
    public User[] getAllUser() {
        return dbAdapter.getAllUser();
    }
}
