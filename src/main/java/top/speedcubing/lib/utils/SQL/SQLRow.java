package top.speedcubing.lib.utils.SQL;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class SQLRow {
    Map<String, Object> o = new HashMap<>();

    public SQLRow(ResultSet result) {
        try {
            ResultSetMetaData metaData = result.getMetaData();
            int l = metaData.getColumnCount();
            for (int i = 1; i <= l; i++)
                o.put(metaData.getColumnName(i), result.getObject(i));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Object get(String s) {
        return o.get(s);
    }

    public Integer getInt(String s) {
        return (Integer) get(s);
    }

    public Double getDouble(String s) {
        return (Double) get(s);
    }

    public Long getLong(String s) {
        return (Long) get(s);
    }

    public Float getFloat(String s) {
        return (Float) get(s);
    }

    public Boolean getBoolean(String s) {
        return (Boolean) get(s);
    }

    public BigDecimal getBigDecimal(String s) {
        return (BigDecimal) get(s);
    }

    public String getString(String s) {
        return (String) get(s);
    }

    public Date getDate(String s) {
        return (Date) get(s);
    }

    public Time getTime(String s) {
        return (Time) get(s);
    }

    public Timestamp getTimestamp(String s) {
        return (Timestamp) get(s);
    }

    public byte[] getByteArray(String s) {
        return (byte[]) get(s);
    }

    public Blob getBlob(String s) {
        return (Blob) get(s);
    }
}
