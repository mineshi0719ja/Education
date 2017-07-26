package jp.co.sss.shop.form;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import jp.co.sss.shop.annotation.CategoryCheck;

@CategoryCheck
public class CategoryForm {

    private Integer id;

    @NotEmpty
    @Size(min = 1, max = 15, message="カテゴリ名は1文字以上15文字以内で入力してください。")
    private String name;

    @Size(max = 30, message="カテゴリ説明文は30文字以内で入力してください。")
    private String description = "";

    private Integer index = 1;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

}
