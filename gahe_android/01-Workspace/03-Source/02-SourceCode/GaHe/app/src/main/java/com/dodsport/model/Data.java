package com.dodsport.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/25.
 */

public class Data  implements Serializable{

    /**
     * emp_sign : 2
     */
    private String emp_sign;

    public String getEmp_sign() {
        return emp_sign;
    }

    public void setEmp_sign(String emp_sign) {
        this.emp_sign = emp_sign;
    }

    public Data() {
    }

    @Override
    public String toString() {
        return "Data{" +
                "emp_sign='" + emp_sign + '\'' +
                '}';
    }
}
