package fr.MatD3mons.BloodyMurder.bdd;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class DataSourceFactory {

    private static MysqlDataSource dataSource;

    public static DataSource getDataSource(){
        if (dataSource == null) {
            dataSource = new MysqlDataSource();
            dataSource.setServerName("localhost");
            dataSource.setPort(3306);
            dataSource.setDatabaseName("BloodyMurder");
            dataSource.setUser("root");
            dataSource.setPassword("");
            InitDatabase();
        }
        return dataSource;
    }

    public static void InitDatabase(){
        try(Connection con = DataSourceFactory.getDataSource().getConnection();
        PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS `player`(" +
                "`uuid` VARCHAR(150) NOT NULL," +
                "`kills` INT NOT NULL," +
                "`argent` INT NOT NULL," +
                "`deaths` INT NOT NULL," +
                "`win` INT NOT NULL," +
                "`lose` INT NOT NULL," +
                "`grade` VARCHAR(150) NOT NULL)"))
        {
            ps.executeUpdate();
        }
        catch (SQLException e){

        }
    }
}
