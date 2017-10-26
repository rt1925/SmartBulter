package imooc.com.smartbulter.entity;

import cn.bmob.v3.BmobUser;

/*
*项目名：SmartBulter
*包名：imooc.com.smartbulter.entity
*文件名：MyUser
*创建者：pavilion15
*创建时间：2017/8/24 2:10
*描述： TODO
*/
public class MyUser extends BmobUser {
    private int age;
    private String desc;
    private boolean sex;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
