package com.liuritian.aigou.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 商品属性
 * </p>
 *
 * @author wbxxx
 * @since 2019-06-29
 */
@TableName("t_specification")
public class Specification extends Model<Specification> {


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 规格名称
     */
    private String specName;
    /**
     * 1 sku属性    2 显示属性
     */
    private Integer type;
    private Long productTypeId;

    //显示属性的值：
    @TableField(exist = false)
    private String  viewValue;


    @TableField(exist = false)
    private String[]  skuValue={};

    public String[] getSkuValue() {
        return skuValue;
    }

    public void setSkuValue(String[] skuValue) {
        this.skuValue = skuValue;
    }

    public String getViewValue() {
        return viewValue;
    }

    public void setViewValue(String viewValue) {
        this.viewValue = viewValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Specification{" +
                ", id=" + id +
                ", specName=" + specName +
                ", type=" + type +
                ", productTypeId=" + productTypeId +
                "}";
    }
}
