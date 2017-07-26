package jp.co.sss.shop.form;

public class BasketForm {

    private Integer id;
    private Integer itemId[];
    private Integer orderNum[];

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer[] getItemId() {
        return itemId;
    }

    public void setItemId(Integer[] itemId) {
        this.itemId = itemId;
    }

    public Integer[] getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer[] orderNum) {
        this.orderNum = orderNum;
    }


}
