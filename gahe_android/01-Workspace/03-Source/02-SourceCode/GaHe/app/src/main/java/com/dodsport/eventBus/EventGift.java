package com.dodsport.eventBus;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class EventGift {

    private int position ;
    private boolean isSelected;
    private String positionImg;

    public String getPositionImg() {
        return positionImg;
    }

    public void setPositionImg(String positionImg) {
        this.positionImg = positionImg;
    }

    public EventGift() {
    }

    public EventGift(int position, boolean isSelected) {
        this.position = position;
        this.isSelected = isSelected;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "EventGift{" +
                "position=" + position +
                ", isSelected=" + isSelected +
                ", positionImg='" + positionImg + '\'' +
                '}';
    }
}
