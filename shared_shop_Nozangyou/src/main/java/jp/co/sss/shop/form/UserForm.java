package jp.co.sss.shop.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import jp.co.sss.shop.annotation.EmailCheck;

@EmailCheck
public class UserForm {
	private Integer id;

	@NotEmpty
	@Email
	private String email;

	@Size(min = 8, max = 16)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String password;

	@NotEmpty
	@Size(min = 1, max = 30)
	private String name;

	@Size(min = 7, max = 8)
    @Pattern(regexp = "^[0-9]+$", message = "{0}は半角数字で入力してください。")
	private String postalCode;

	@Size(min = 1, max = 150)
	private String address;

	@Size(min = 10, max = 11)
	@Pattern(regexp = "^[0-9]+$", message = "{0}は半角数字で入力してください。")
	private String phoneNumber;

	private Integer authority;

	private String cardNo;
	private String cardPeriodMonth;
	private String cardPeriodYear;
	private String cardSecurityCode;
	private Integer cardType;
	private Integer deleteFlag;
	private String insertDate;
	private Integer index = 1;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
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

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

}
