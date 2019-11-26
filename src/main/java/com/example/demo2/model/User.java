package com.example.demo2.model;

import com.example.demo2.utils.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 用户类
 * @Author zhenghao
 * @Date 2019/9/12 11:30
 */
public class User implements Serializable {

    private static final long serialVersionUID = -8867831003822660894L;
    private Long id;
    private Integer age;
    private String name;
    private String password;
    private Date birthday;

    public User() {
    }

    public User(Long id, Integer age, String name, String password, Date birthday) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.password = password;
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + DateUtil.dateFormat(birthday) +
                '}';
    }
}
