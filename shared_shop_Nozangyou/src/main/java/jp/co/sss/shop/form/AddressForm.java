package jp.co.sss.shop.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class AddressForm {

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

}
