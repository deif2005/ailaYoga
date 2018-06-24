package com.dod.sport.util;

/**
 * Created by defi on 2016/7/7.
 * 用途；用来对应取值序号常量值，以便直观了解取值的具体内容
 */
public enum EnumOrdinal {
    /***********************系统级枚举类型定义****************/
    //消息代码说明:1,操作成功;2,该操作需要登录;3,您没有该项操作的权利;
    //4,操作参数错误，请更正后重试;5,操作失败，请重试
    success("1"),needlogin("2"),noauth("3"),paramerror("4"),exception("5"){
        @Override
        public boolean isRest(){
                return true;
        }
    },
    //对应status状态：1正常，2删除，3停用或其它'
    normal("1"), delete("2"), other("3"){
        @Override
        public boolean isRest() {
            return true;
        }
    },
    //客户端锁定状态:1，锁定；2，未锁定
    locked("1"), unlock("2"){
        @Override
        public boolean isRest(){return true;}
    };
    /***********************系统级枚举类型定义****************/

    private String value;

    private EnumOrdinal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isRest() {
        return false;
    }
}

