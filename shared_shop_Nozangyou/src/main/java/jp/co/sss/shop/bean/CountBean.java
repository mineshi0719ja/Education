package jp.co.sss.shop.bean;

public class CountBean {

    private Integer index;

    private boolean isCurrent;

    public CountBean(Integer index, boolean isCurrent) {
        this.index = index;
        this.isCurrent = isCurrent;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public boolean getIsCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean isCurrent) {
        this.isCurrent = isCurrent;
    }

}
