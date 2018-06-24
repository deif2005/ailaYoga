package com.dod.sport.service.impl;

import com.dod.sport.config.Configuration;
import com.dod.sport.constant.SysConfigConstants;
import com.dod.sport.dao.IBusinessDao;
import com.dod.sport.dao.IClientUserDao;
import com.dod.sport.domain.common.BatchDataPage;
import com.dod.sport.domain.common.BusiException;
import com.dod.sport.domain.po.Base.BaseBusinessInfo;
import com.dod.sport.domain.po.Base.BusinessInfo;
import com.dod.sport.domain.po.Base.ClientUser;
import com.dod.sport.domain.po.Base.StoreInfo;
import com.dod.sport.domain.po.Member.MembercardRelation;
import com.dod.sport.domain.po.ResponseMember;
import com.dod.sport.service.IBusinessService;
import com.dod.sport.service.IClientUserService;
import com.dod.sport.service.IMemberService;
import com.dod.sport.util.BusiUtils;
import com.dod.sport.util.CommonEnum;
import com.dod.sport.util.RedisCommon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 平台用户业务层
 * Created by Administrator on 2017/10/9.
 */
@Service
public class ClientUserServiceImpl implements IClientUserService {

    @Autowired
    IClientUserDao clientUserDao;
    @Autowired
    IBusinessDao businessDao;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IBusinessService businessService;
    /**
     * 注册平台用户
     * @param clientUser
     */
    @Override
    public void addClientUser(ClientUser clientUser) {
        if (clientUser.getId()==null||clientUser.getId()== ""){
            clientUser.setId( UUID.randomUUID().toString());
        }
        String userSerialId = RedisCommon.getMaxClientUserId();
        if(userSerialId == ""||userSerialId==null){
            String maxClientUserId = clientUserDao.getMaxClientUserId();
            userSerialId = RedisCommon.setAndReturnMaxClientUserId(maxClientUserId);
        }
        clientUser.setUserSerialId(userSerialId);
        clientUserDao.addClientUser(clientUser);
    }

    /**
     * 平台用户登陆
     * @param clientUser
     * @return
     */
    @Override
    public ResponseMember loginClientUserInfo(ClientUser clientUser) {
        ResponseMember queryclientUser = clientUserDao.loginClientUserInfo(clientUser.getPhoneNum(), clientUser.getPassword());
        if (queryclientUser==null){
            throw new BusiException(CommonEnum.ReturnCode.BusinessCode.busi_err_passworderror.getValue());
        }
        queryclientUser.setHeadPortrait(Configuration.getStaticShowPath()+queryclientUser.getHeadPortrait());
        List<ResponseMember> responseMembers = memberService.listMemberInfoByUserId(queryclientUser.getId());
        if (responseMembers !=null && responseMembers.size()>0){
            return responseMembers.get(0);
        }
        return queryclientUser;
    }

    /**
     * 获取平台用户详情
     * @param clientUser
     * @return
     */
    @Override
    public ClientUser getClientUserInfo(ClientUser clientUser) {
        return clientUserDao.getClientUserInfo(clientUser);
    }

    /**
     * 获取用户的商家列表
     * @param phoneNum
     * @return
     */
    @Override
    public List<BusinessInfo> listBusinessByPhoneNum(String phoneNum) {
        List<BusinessInfo> businessInfoList= businessDao.getBusinessListByPhoneNum(phoneNum);
        if (businessInfoList !=null && businessInfoList.size()>0){
            for(BaseBusinessInfo baseBusinessInfo :businessInfoList){
                List<StoreInfo>list = businessDao.listStoreByBusinessId(baseBusinessInfo.getId());
                baseBusinessInfo.setStoreInfoList(list);
            }
        }
        return businessInfoList;
    }


    /**
     * 获取平台用户列表
     * @param sex
     * @param page
     * @return
     */
    @Override
    public List<ClientUser> listClientUserInfo(String sex, Integer page, Integer pageSize) {
        BatchDataPage batchDataPage = new BatchDataPage();
        batchDataPage.setPage(page);
        batchDataPage.setPagesize(pageSize);
        return clientUserDao.listClientUserInfo(sex,batchDataPage.getOffset(),
                batchDataPage.getPagesize());
    }

    /**
     * 更新平台用户信息
     * @param clientUser
     */
    @Override
    public void updateClientUser(ClientUser clientUser) {
        clientUserDao.updateClientUser(clientUser);
    }

    /**
     * 根据电话获取平台用户信息
     * @param phoneNum
     * @return
     */
    @Override
    public ClientUser getClientUserByPhone(String phoneNum) {
        return clientUserDao.getClientUserByPhone(phoneNum);
    }

    /**
     * 获取平台用户所有商家所有门店
     * @param phoneNum
     * @return
     */
    @Override
    public List<BaseBusinessInfo> listClientUserBusiness(String phoneNum) {
        List<BaseBusinessInfo>baseBusinessInfos = clientUserDao.listClientUserBusiness(phoneNum);
        if (baseBusinessInfos!=null && baseBusinessInfos.size()>0){
            for (BaseBusinessInfo baseBusinessInfo :baseBusinessInfos){
                List<StoreInfo> storeInfos = businessService.listStoreByBusinessId(baseBusinessInfo.getId());
                baseBusinessInfo.setStoreInfoList(storeInfos);
            }
        }
        return baseBusinessInfos;
    }

    /**
     * 获取用户的会员卡列表
     * @param userId
     * @param businessId
     * @return
     */
    @Override
    public List<MembercardRelation> listUserMembercard(String userId, String businessId) throws ParseException {
        List<MembercardRelation> list = clientUserDao.listUserMembercard(userId, businessId);
        DateFormat df = new SimpleDateFormat(SysConfigConstants.DATE_FORMAT_FORDATE);
        String nowDate = df.format(new Date());
        if (list != null && list.size() > 0) {
            for (MembercardRelation membercardrelation : list) {
                if (membercardrelation.getMembcardType().equals(CommonEnum.MemberCard.membcardType.type_days.getValue())) {//期限卡
                    String validaityTime = membercardrelation.getValidityTime();
                    //算出还剩多少天到期
                    if (validaityTime != null) {
                        long days = BusiUtils.getDays(nowDate, validaityTime.substring(0, 19));
                        if (days >= 0) {
                            membercardrelation.setDays(String.valueOf(days));
                        }
                    }
                } else if (membercardrelation.getMembcardType().equals(CommonEnum.MemberCard.membcardType.type_times.getValue())) {
                    Integer theTimes = 0;
                    Integer giveTimes = 0;
                    if (membercardrelation.getTimes() != null) {
                        theTimes = Integer.valueOf(membercardrelation.getTimes());
                    }
                    if (membercardrelation.getGiveTimes() != null) {
                        giveTimes = Integer.valueOf(membercardrelation.getGiveTimes());
                    }
                    Integer sumTimes = theTimes + giveTimes;
                    membercardrelation.setTimes(String.valueOf(sumTimes));
                }
            }
        }
        return list;
    }
}
