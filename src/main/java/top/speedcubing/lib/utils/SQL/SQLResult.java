package top.speedcubing.lib.utils.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLResult {
    private final List<SQLRow> rows = new ArrayList<>();

    public SQLResult(ResultSet result) {
        try {
            while (result.next())
                rows.add(new SQLRow(result));
            result.getStatement().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SQLRow get(int i) {
        return rows.get(i);
    }
}
