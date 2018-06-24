package com.dod.sport.dao;

import com.dod.sport.domain.po.SystemNotice;
import org.springframework.stereotype.Repository;

/**
 * Created by defi on 2017-08-28.
 * 消息管理dao
 */
@Repository
public interface IMessageDao {

    /**
     * 添加系统通知信息
     * @param systemNotice
     */
    public void addSystemNoticeInfo(SystemNotice systemNotice);

    /**
     * 获取系统消息流水号
     * @return
     */
    public String getSystemNoticeId();
}
