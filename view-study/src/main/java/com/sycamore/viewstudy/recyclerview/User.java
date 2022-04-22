package com.sycamore.viewstudy.recyclerview;

/**
 * @author dingyx
 * @description: 用户信息
 * @date: 2022/4/22
 */
public class User {

    public static final String KEY_ID = "key_id";
    public static final String KEY_NAME = "key_name";
    public static final String KEY_PROFESSION = "key_profession";

    public Long id;
    public String name;
    public String profession;

    public User(long id, String name, String profession) {
        this.id = id;
        this.name = name;
        this.profession = profession;
    }

}
