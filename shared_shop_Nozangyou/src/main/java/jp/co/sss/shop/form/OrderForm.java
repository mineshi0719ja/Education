package jp.co.sss.shop.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotEmpty;

public class OrderForm {

    private Integer id;

    @Size(min = 7, max = 8)
    @Pattern(regexp = "^[0-9]+$", message = "{0}は半角数字で入力してください。")
    private String postalCode;

    @Size(min = 1, max = 150)
    private String address;

    @NotEmpty
    @Size(min = 1, max = 30)
    private String name;

    @Size(min = 10, max = 11)
    @Pattern(regexp = "^[0-9]+$", message = "{0}は半角数字で入力してください。")
    private String phoneNumber;

    @NotNull
    private Integer payMethod;

    private Integer userId;

    @Pattern(regexp = "^[0-9]{16}$", message = "正しいカード番号を入力してください。")
    private String cardNo;

    @NotEmpty
    private String cardPeriodMonth;

    @NotEmpty
    private String cardPeriodYear;

    @Pattern(regexp = "^[0-9]{3}$", message = "{0}は半角数字で3桁で入力してください。")
    private String cardSecurityCode;

    @NotNull
    private Integer cardType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardPeriodMonth() {
        return cardPeriodMonth;
    }

    public void setCardPeriodMonth(String cardPeriodMonth) {
        this.cardPeriodMonth = cardPeriodMonth;
    }

    public String getCardPeriodYear() {
        return cardPeriodYear;
    }

    public void setCardPeriodYear(String cardPeriodYear) {
        this.cardPeriodYear = cardPeriodYear;
    }

    public String getCardSecurityCode() {
        return cardSecurityCode;
    }

    public void setCardSecurityCode(String cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }
}
