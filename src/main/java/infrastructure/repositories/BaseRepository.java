package infrastructure.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.IDatabase;
import database.query.IQuery;
import domains.images.Image;
import infrastructure.IModel;
import infrastructure.specifications.ISpecification;

public abstract class BaseRepository<TModel extends IModel> implements IRepository<TModel> {

	private IDatabase database;

	public BaseRepository(IDatabase database){
		this.database = database;
	}
	
	public void Add(TModel model){
		IQuery query = ModelToQuery(model);
		database.executeUpdate(query);
	}
	
	public List<TModel> Retrieve(ISpecification specifications) {
		ResultSet dbresults = database.executeQuery(SpecificationsToQuery(specifications));
		ArrayList<TModel> results = new ArrayList<TModel>();
		try {
			while(dbresults.next()){
				results.add(ReadFromDb(dbresults));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public abstract IQuery SpecificationsToQuery(ISpecification specifications);
	public abstract TModel ReadFromDb(ResultSet dbResult) throws SQLException;
	public abstract IQuery ModelToQuery(TModel model);
}
