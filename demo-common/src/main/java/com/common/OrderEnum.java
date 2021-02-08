package com.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Desc
 * @Date 2021/1/27 11:20
 **/
@Getter
@AllArgsConstructor
public enum OrderEnum {

    MUTEX_CHECK("MUTEX_CHECK", "互斥检查", 11),
    PRODUCT_CHECK("PRODUCT_CHECK", "授信申请产品要素校验", 21),
    ELECTRIC_SIGN("ELECTRIC_SIGN", "授信申请电子签章", 3),
    PRE_CHOOSE("PRE_CHOOSE", "小贷XD前筛", 4),
    PEOPLE_BANK_QUERY("PEOPLE_BANK_QUERY", "保险人行信息查询", 5),
    POLICY_DECISION("POLICY_DECISION", "小贷XD决策", 6),
    SYN_INSURANCE_QUOTA("SYN_INSURANCE_QUOTA", "保险额度同步", 7),
    SYN_ACCOUNT_QUOTA("SYN_ACCOUNT_QUOTA", "小贷账户系统额度同步", 8),
    FUND_PLAN_FREEZE("FUND_PLAN_FREEZE", "资金计划冻结", 9),
    QUOTA_FREEZE("QUOTA_FREEZE", "额度冻结", 1);


    private String code;
    private String message;
    private Integer index;


    public OrderEnum getProcessTypeEnum(String code) {
        OrderEnum[] var2 = OrderEnum.values();
        int var3 = var2.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            OrderEnum value = var2[var4];
            if (value.getCode().equals(code)) {
                return value;
            }
        }

        return null;
    }


}
