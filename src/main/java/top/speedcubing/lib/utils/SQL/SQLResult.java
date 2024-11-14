package top.speedcubing.lib.utils.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public class SQLResult implements Iterable<SQLRow> {
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

    public int rowCnt() {
        return rows.size();
    }

    public SQLRow get(int i) {
        return rows.get(i);
    }

    @NotNull
    @Override
    public Iterator<SQLRow> iterator() {
        return rows.iterator();
    }

    @Override
    public void forEach(Consumer<? super SQLRow> action) {
        rows.forEach(action);
    }
}
