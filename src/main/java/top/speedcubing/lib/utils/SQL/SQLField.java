package top.speedcubing.lib.utils.SQL;

import java.math.BigDecimal;
import java.sql.Blob;

public class SQLField {
    private final String columnName;
    private final Object object;

    public SQLField(String columnName, Object object) {
        this.columnName = columnName;
        this.object = object;
    }

    public String getColumnName() {
        return columnName;
    }

    public Object getObject() {
        return object;
    }

    public BigDecimal getBigDecimal() {
        return (BigDecimal) object;
    }

    public Blob getBlob() {
        return (Blob) object;
    }

    public Boolean getBoolean() {
        return (Boolean) object;
    }

    public byte[] getBytes() {
        return (byte[]) object;
    }

    public Double getDouble() {
        return (Double) object;
    }

    public Float getFloat() {
        return (Float) object;
    }

    public Integer getInt() {
        if (object instanceof BigDecimal) {
            return ((BigDecimal) object).intValue();
        }
        if (object instanceof Long) {
            return ((Long) object).intValue();
        }
        return (Integer) object;
    }

    public Long getLong() {
        if (object instanceof BigDecimal) {
            return ((BigDecimal) object).longValue();
        }
        return (Long) object;
    }

    public String getString() {
        return object == null ? null : object.toString();
    }
}
