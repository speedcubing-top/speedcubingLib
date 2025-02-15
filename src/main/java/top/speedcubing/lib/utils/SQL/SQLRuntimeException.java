package top.speedcubing.lib.utils.SQL;

import java.sql.SQLException;
import java.util.Collection;

public class SQLRuntimeException extends RuntimeException {

    public SQLRuntimeException(SQLException cause) {
        super(cause);
    }

    public SQLRuntimeException(Collection<String> sql, SQLException cause) {
        this(sql.toString(), cause);
    }

    public SQLRuntimeException(String sql, SQLException cause) {
        super("Failed to execute query: " + sql, cause);
    }
}
