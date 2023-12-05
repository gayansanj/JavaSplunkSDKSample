package com.dhl.esb.rnt.logging.util;

/*
 * @created 31/07/2023 - 12:51 PM
 * @project RNTSplunkQueryExecutor
 * @author Gayan Sanjeewa
 */
public enum TypeEnum {

    SP_NAME("ShipperName"), SP_CON_NAME("ShipperContactName"), CS_ORG_NAME("ConsigneeOrgName"), CS_CON_NAME("ConsigneeContactName");
    public String typeName;

    private TypeEnum(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public static TypeEnum fromValue(String typeName) {
        for (TypeEnum typeEnum : TypeEnum.values()
        ) {
            if (typeEnum.getTypeName().equals(typeName)) {
                return typeEnum;
            }
        }
        return null;
    }
}
