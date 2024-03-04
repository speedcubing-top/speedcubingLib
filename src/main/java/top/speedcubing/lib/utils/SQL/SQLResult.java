package top.speedcubing.lib.utils.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SQLResult {
    List<SQLRow> rows;
    ResultSet result;

    public SQLResult(ResultSet result) {
        this.result = result;
        try {
            while (result.next())
                rows.add(new SQLRow(result));
            result.getStatement().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SQLRow get(int i){
        return rows.get(i);
    }

    public ResultSet getResultSet(){
        return result;
    }
}
