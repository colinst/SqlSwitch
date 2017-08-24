package com.recruit.model;

/**
 * @author 郭宏禧
 * @createTime 2017/8/18
 * @context **
 */
public class Column {

    private String Field;
    private String Type;
    private String Null;
    private String Key;
    private String Default;
    private String Extra;

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getNull() {
        return Null;
    }

    public void setNull(String aNull) {
        Null = aNull;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getDefault() {
        return Default;
    }

    public void setDefault(String aDefault) {
        Default = aDefault;
    }

    public String getExtra() {
        return Extra;
    }

    public void setExtra(String extra) {
        Extra = extra;
    }

    @Override
    public String toString() {
        return "Table{" +
                "Field='" + Field + '\'' +
                ", Type='" + Type + '\'' +
                ", Null='" + Null + '\'' +
                ", Key='" + Key + '\'' +
                ", Default='" + Default + '\'' +
                ", Extra='" + Extra + '\'' +
                '}';
    }
}
