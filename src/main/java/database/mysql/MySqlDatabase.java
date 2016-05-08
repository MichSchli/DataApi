package database.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import database.IDatabase;
import database.IDatabaseResult;
import database.query.IQuery;

public class MySqlDatabase implements IDatabase {

	private MysqlDataSource dataSource;
	private Connection conn;

	public MySqlDatabase(MySqlDatabaseConfiguration config) {
		dataSource = new MysqlDataSource();
		dataSource.setUser(config.UserName);
		dataSource.setPassword(config.Password);
		dataSource.setServerName("localhost");
		dataSource.setDatabaseName(config.ServerName);
		
		try {
			conn = dataSource.getConnection();
			Statement stmt = conn.createStatement();
			/*
			int rs = stmt.executeUpdate("insert into images (id, path) values ('abc123', 'hej@hej.hej')");
			
			System.out.println(rs);
			*/
			ResultSet rs2 = stmt.executeQuery("SELECT ID FROM images");
			
			rs2.first();
			System.out.println(rs2.getString("id"));
			rs2.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void executeUpdate(IQuery query){
		// TODO Auto-generated method stub
		
	}

	public IDatabaseResult executeQuery(IQuery query){
		Statement stmt;
		try {
			stmt = conn.createStatement();
			System.out.println(query.process());
			ResultSet rs2 = stmt.executeQuery(query.process()+ ";");
			return new MySqlDatabaseResult(rs2);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
