package com.sycamore.viewstudy.recyclerview;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dingyx
 * @description: mock 用户数据
 * @date: 2022/4/22
 */
public class UserRepo {

    public static List<User> USER_LIST = new ArrayList<>();
    public static List<User> USER_LIST2 = new ArrayList<>();


    static {
        USER_LIST.add(create(0, "Lily", "Java Engineer"));
        USER_LIST.add(create(1, "Chen", "iOS Engineer"));
        USER_LIST.add(create(2, "Jay", "Python Engineer"));
        USER_LIST.add(create(3, "Liu", "Designer"));
        USER_LIST.add(create(4, "Hans", "Java Engineer"));
        USER_LIST.add(create(5, "Joey", "Python Engineer"));
        USER_LIST.add(create(6, "Mauri", "Java Engineer"));
        USER_LIST.add(create(7, "Zhang", "Java Engineer"));
        USER_LIST.add(create(8, "Chen", "Java Engineer"));
        USER_LIST.add(create(9, "Rhys", "Android Engineer"));
        USER_LIST.add(create(10, "Bons", "Designer"));



        USER_LIST2.add(create(0, "Lily", "Java Engineer"));
        USER_LIST2.add(create(1, "Chen", "iOS Engineer"));
        USER_LIST2.add(create(2, "Jay", "Python Engineer"));
        USER_LIST2.add(create(3, "Liu", "Designer"));
        USER_LIST2.add(create(4, "Hans", "Java Engineer"));
        USER_LIST2.add(create(5, "Joey", "Python Engineer"));
        USER_LIST2.add(create(6, "Mauri", "Java Engineer"));
        USER_LIST2.add(create(7, "Zhang", "Java Engineer"));
        USER_LIST2.add(create(8, "Chen", "Java Engineer"));
        USER_LIST2.add(create(9, "Rhys", "Android Engineer"));
        USER_LIST2.add(create(10, "Bons", "Designer"));
    }


    public static User create(long id, String name, String profession) {
        return new User(id, name, profession);
    }

}
