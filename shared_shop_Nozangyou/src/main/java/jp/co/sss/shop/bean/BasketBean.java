package jp.co.sss.shop.bean;

public class BasketBean {

    private Integer id;
    private String name;
    private Integer stock;
    private Integer orderNum = 1;

    public BasketBean() {}

    public BasketBean(Integer id, String name, Integer stock) {
        this.id = id;
        this.name = name;
        this.stock = stock;
    }

    public BasketBean(Integer id, String name, Integer stock, Integer orderNum) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.orderNum = orderNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

}
