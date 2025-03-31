package com.yjx.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="MedicineSynopsis对象", description="药品说明书")
public class MedicineSynopsis implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "药品名称")
    private String name;

    @ApiModelProperty(value = "主要成分")
    private String mainIngredients;

    @ApiModelProperty(value = "性状")
    private String traits;

    @ApiModelProperty(value = "适应症/主治功能")
    private String indications;

    @ApiModelProperty(value = "规格型号")
    private String standard;

    @ApiModelProperty(value = "用法用量")
    private String dosage;

    @ApiModelProperty(value = "不良反应")
    private String adverseReactions;

    @ApiModelProperty(value = "禁 忌")
    private String taboo;

    @ApiModelProperty(value = "注意事项")
    private String precautions;

    @ApiModelProperty(value = "药物互相作用")
    private String interaction;

    @ApiModelProperty(value = "儿童用药")
    private String childrenMedication;

    @ApiModelProperty(value = "老年患者用药")
    private String oldMedication;

    @ApiModelProperty(value = "孕妇及哺乳期用药")
    private String pregnancyMedication;

    @ApiModelProperty(value = "药物过量")
    private String overdose;

    @ApiModelProperty(value = "药理毒理")
    private String pharmacology;

    @ApiModelProperty(value = "药代动力学")
    private String pharmacokinetics;

    @ApiModelProperty(value = "贮 藏")
    private String storage;

    @ApiModelProperty(value = "包 装")
    private String box;

    @ApiModelProperty(value = "有效期")
    private String validPeriod;

    @ApiModelProperty(value = "执行标准")
    private String executiveStandard;

    @ApiModelProperty(value = "批准文号")
    private String approvalNumber;

    @ApiModelProperty(value = "生产厂家")
    private String manufacturer;

    @ApiModelProperty(value = "版本号")
    @Version
    private Integer version;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer deleted;

    @ApiModelProperty(value = "添加时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
