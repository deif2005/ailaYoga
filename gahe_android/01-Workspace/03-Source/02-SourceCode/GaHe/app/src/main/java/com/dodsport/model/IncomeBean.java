package com.dodsport.model;

/**
 * Created by Administrator on 2017/7/26.
 */

public class IncomeBean {

    private int provinceCount;
    private String provinceName;

    public int getProvinceCount() {
        return provinceCount;
    }

    public void setProvinceCount(int provinceCount) {
        this.provinceCount = provinceCount;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public IncomeBean() {
    }

    @Override
    public String toString() {
        return "IncomeBean{" +
                "provinceCount=" + provinceCount +
                ", provinceName='" + provinceName + '\'' +
                '}';
    }
}
