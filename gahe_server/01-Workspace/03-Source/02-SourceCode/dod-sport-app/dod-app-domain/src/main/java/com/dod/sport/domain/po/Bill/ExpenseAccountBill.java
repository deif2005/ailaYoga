package com.dod.sport.domain.po.Bill;

import com.dod.sport.domain.po.Bill.BaseBill;

/**
 * Created by defi on 2017-08-31.
 * 报销单pojo
 */
public class ExpenseAccountBill extends BaseBill {
    private String account;  //报销金额
    private String accountBill;  //报销单据

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccountBill() {
        return accountBill;
    }

    public void setAccountBill(String accountBill) {
        this.accountBill = accountBill;
    }
}
