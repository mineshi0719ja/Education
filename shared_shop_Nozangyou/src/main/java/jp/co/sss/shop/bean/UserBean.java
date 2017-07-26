package jp.co.sss.shop.bean;

public class UserBean {
	private Integer id;
	private String email;
	private String password;
	private String name;
	private String postalCode;
	private String address;
	private String phoneNumber;
	private Integer authority;
	private String cardNo;
	private String cardPeriodMonth;
	private String cardPeriodYear;
	private String cardSecurityCode;
	private Integer cardType;
	private Integer deleteFlag;
	private String insertDate;

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
}
