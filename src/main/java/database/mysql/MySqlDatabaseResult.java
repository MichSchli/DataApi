package database.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseException;
import database.IDatabaseResult;

public class MySqlDatabaseResult implements IDatabaseResult {

	private ResultSet resultSet;

	public MySqlDatabaseResult(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
	
	@Override
	public boolean next() throws DatabaseException {
		try {
			return resultSet.next();
		} catch (SQLException e) {
			throw new DatabaseException();
		}
	}

	@Override
	public int getInt(String column) throws DatabaseException {
		try {
			return resultSet.getInt(column);
		} catch (SQLException e) {
			throw new DatabaseException();
		}
	}

	@Override
	public String getString(String column) throws DatabaseException {
		try {
			return resultSet.getString(column);
		} catch (SQLException e) {
			throw new DatabaseException();
		}
	}

}
