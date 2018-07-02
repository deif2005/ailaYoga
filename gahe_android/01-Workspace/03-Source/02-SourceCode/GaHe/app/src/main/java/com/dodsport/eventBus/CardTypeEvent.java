package com.dodsport.eventBus;

/**
 * Created by Administrator on 2017/9/14.
 * 卡券设置
 */
public class CardTypeEvent {

    private int mPosition;
    private String msg;
    private String Type;
    private String Name;
    private String CardType;
    private String Positions;


    public CardTypeEvent(int position, String msg, String type, String name, String cardType,String Positions) {
        mPosition = position;
        this.msg = msg;
        Type = type;
        Name = name;
        CardType = cardType;
        Positions = Positions;
    }

    public CardTypeEvent() {
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCardType() {
        return CardType;
    }

    public void setCardType(String cardType) {
        CardType = cardType;
    }


    public String getPositions() {
        return Positions;
    }

    public void setPositions(String positions) {
        Positions = positions;
    }

    @Override
    public String toString() {
        return "CardTypeEvent{" +
                "mPosition=" + mPosition +
                ", msg='" + msg + '\'' +
                ", Type='" + Type + '\'' +
                ", Name='" + Name + '\'' +
                ", CardType='" + CardType + '\'' +
                ", Position='" + Positions + '\'' +
                '}';
    }
}
