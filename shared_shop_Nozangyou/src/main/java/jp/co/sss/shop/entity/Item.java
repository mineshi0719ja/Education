package jp.co.sss.shop.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_items_gen")
	@SequenceGenerator(name = "seq_items_gen", sequenceName = "seq_items", allocationSize = 1)
	private Integer id;

	@Column
	private String name;

	@Column
	private Integer price;

	@Column
	private String description;

	@Column
	private Integer stock;

	@Column
	private String image;

	@Column(insertable = false)
	private Integer deleteFlag;

	@Column(insertable = false, updatable = false)
	private Date insertDate;

	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id")
	private Category category;

	@OneToMany(mappedBy = "item")
	private List<OrderItem> orderItemList;

	public Item (){

	}

	public Item(Integer id, String name, String description, String image, String category_name) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.category = new Category();
		this.category.setName(category_name);
	}

	public Item(Integer id, String name, Integer price, String description, String image, String category_name) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.image = image;
		this.category = new Category();
		this.category.setName(category_name);
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

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<OrderItem> getOrderItemsList() {
		return orderItemList;
	}

	public void setOrderItemsList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
}
