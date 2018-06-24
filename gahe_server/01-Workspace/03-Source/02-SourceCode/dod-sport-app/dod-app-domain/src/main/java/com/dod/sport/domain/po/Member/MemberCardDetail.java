package com.dod.sport.domain.po.Member;

/**
 * \* Description:会员卡信息详情
 * \
 */
public class MemberCardDetail extends MemberCard{

    private String membcardType;   //会员卡类型:1:次卡;2:期限卡
    private String times;               //次卡次数
    private String validityTime;     //有效期时间
    private String businessName;   //商家名称

    @Override
    public String getMembcardType() {
        return membcardType;
    }

    @Override
    public void setMembcardType(String membcardType) {
        this.membcardType = membcardType;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(String validityTime) {
        this.validityTime = validityTime;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}