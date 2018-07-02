package com.dodsport.model;

/**
 * Created by Administrator on 2017/8/1.
 * Token实体类
 */

public class TokenBean {


        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;

    }

    public TokenBean() {
    }

    @Override
    public String toString() {
        return "TokenBean{" +
                "token='" + token + '\'' +
                '}';
    }
}
