package com.guc.mylibrary.widgets.contacts;

import java.util.Comparator;

/**
 *
 * Author:guc
 * Time:2018/5/20
 * Description:
 * //@标签代表A前面的那些，#代表除了A-Z以外的其他标签
 */
public class CompareSort implements Comparator<UserKH> {
    @Override
    public int compare(UserKH user1, UserKH user2) {
        if(user1.letter.equals("@") || user2.letter.equals("@")){
            //通讯录前面的ｉｔｅｍ(公众号，标签......)
            return user1.letter.equals("@") ? -1:1;
        }
        //user1属于#标签，放到后面
        else if(!user1.letter.matches("[A-z]+")){
            return 1;
            //user2属于#标签，放到后面
        }else if(!user2.letter.matches("[A-z]+")){
            return -1;
        }else {
            return user1.letter.compareTo(user2.letter);
        }
    }
}
