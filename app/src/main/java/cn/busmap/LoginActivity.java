package cn.busmap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.busmap.control.UserController;
import cn.busmap.model.User;


public class LoginActivity extends AppCompatActivity {
    private EditText et_username;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        //回显用户名和密码
        readAccount();
    }

    public void readAccount() {
        SharedPreferences sp = getSharedPreferences("password", MODE_PRIVATE);
        String name = sp.getString("username", "");
        String pass = sp.getString("password", "");
        et_username.setText(name);
        et_password.setText(pass);
    }


    public void login(View v) {
        String name = et_username.getText().toString().trim();
        String pass = et_password.getText().toString().trim();

        UserController userController = new UserController(LoginActivity.this);
        User[] user = userController.QueryOneByName(name);
        if (name.equals("") || pass.equals("")) {
            new android.app.AlertDialog.Builder(LoginActivity.this).setMessage("请填写完整").show();
        } else {
            if (user == null) {
                Toast.makeText(this, "用户不存在", Toast.LENGTH_SHORT).show();

            } else if (user[0].getUsername().equals(name) && user[0].getPassword().equals(pass)) {
                //把用户名和密码记在本地，用于密码回显
                SharedPreferences sp = getSharedPreferences("password", MODE_PRIVATE);//存储路径在data/data/cn.cnu.smartbook/share_prefs
                //拿到sp的编辑器
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("username", name);
                ed.putString("password", pass);
                //提交
                ed.commit();
                //创建并显示吐司对话框
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MainLocationActivity.class);
                startActivity(intent);
            } else if (!user[0].getPassword().equals(pass)) {
                Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void register(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
