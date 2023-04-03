import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class JdbcImple {
    public DataSource dataSource(){
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser("postgres");
        dataSource.setPassword("03052004");
        dataSource.setDatabaseName("postgres");
        return dataSource;
    }
}
