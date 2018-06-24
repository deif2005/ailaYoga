package com.dod.sport.constant;

/**
 * SocketConstants
 * socket常量类
 *
 * @author yuhao
 * @date 2016/9/19 19:40
 */
public class SocketConstants {

    /**
     * 返回成功标识
     */
    public static final String SUCCESS = "success";

    /**
     * 返回失败标识
     */
    public static final String FAILED = "failed";
    /**
     * 返回成功标识
     */
    public static final String SUCCESS_TRUE = "true";

    /**
     * 返回失败标识
     */
    public static final String FAILED_FALSE = "false";

    /**
     * socket服务端发送消息标识
     */
    public static final String SERVER_FLAG = "server";

    /**
     * 消息key：打开客户端
     */
    public static final String OPEN_CLIENT = "openClient";

    /**
     * 消息key：关闭客户端
     */
    public static final String CLOSE_CLIENT = "closeClient";

    /**
     * 消息key：重启客户机
     */
    public static final String RESET_PC = "resetPC";

    /**
     * 消息key：关闭客户机
     */
    public static final String CLOSE_PC = "closePC";

    /**
     * 消息key：开始考试
     */
    public static final String START_EXAM = "startExamine";

    /**
     * 消息key：结束考试
     */
    public static final String END_PAPER = "stopExamine";


    /**
     * 消息key：收集座位信息
     */
    public static final String COLLECT_INFO = "seatInfo";

    /**
     * 来自监考合并 2016-11-8 aisq-------------------------
     * 消息key：收集座位信息
     */
    public static final String COLLECT_SEAT = "collectSeat";

    /**
     * 消息key：锁定/解锁
     */
    public static final String LOCK_SCREEN = "lockScreen";

    /**
     * 重新登陆
     */
    public static final String RESET_LOGIN = "resetLogin";

    /**
     * 网络检测,心跳命令处理
     */
    public static final String NETWORK_CHECK_= "networkCheck";
}
