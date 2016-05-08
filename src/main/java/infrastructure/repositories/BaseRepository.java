package infrastructure.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import database.DatabaseException;
import database.IDatabase;
import database.IDatabaseResult;
import database.query.IQuery;
import database.query.SelectQuery;
import database.query.condition.WhereInQuery;
import infrastructure.IModel;
import infrastructure.fill.FillException;
import infrastructure.fill.IDataFiller;
import infrastructure.map.IDataMapper;
import infrastructure.specifications.ISpecification;

public abstract class BaseRepository<TModel extends IModel, TSpecification extends ISpecification>
		implements IRepository<TModel, TSpecification> {

	private IDatabase database;
	private ArrayList<IDataMapper<TModel>> dataMappers;
	private ArrayList<IDataFiller<TModel>> dataFillers;

	public BaseRepository(IDatabase database) {
		this.database = database;
		dataMappers = new ArrayList<IDataMapper<TModel>>();
		dataFillers = new ArrayList<IDataFiller<TModel>>();
	}

	public void Add(TModel model) {
		IQuery query = ModelToQuery(model);
		database.executeUpdate(query);
	}

	protected void addMapper(IDataMapper<TModel> mapper) {
		dataMappers.add(mapper);
	}

	protected void addFiller(IDataFiller<TModel> filler) {
		dataFillers.add(filler);
	}

	@Override
	public List<Integer> Search(TSpecification specification) {
		IQuery query = SpecificationsToQuery(specification);
		IDatabaseResult dbresults = database.executeQuery(query);
		ArrayList<Integer> results = new ArrayList<Integer>();
		try {
			while (dbresults.next()) {
				results.add(dbresults.getInt("id"));
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public List<TModel> Retrieve(List<Integer> ids) {
		IDatabaseResult dbresults = database.executeQuery(IdentifiersToQuery(ids));
		ArrayList<TModel> results = new ArrayList<TModel>();
		try {
			while (dbresults.next()) {
				results.add(ReadFromDb(dbresults));
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		}

		for (IDataMapper<TModel> mapper : dataMappers) {
			mapper.doMap(results);
		}

		try {
			for (IDataFiller<TModel> filler : dataFillers) {
				filler.doFill(results);
			}
		} catch (FillException e) {
			e.printStackTrace();
		}

		results = sortByIds(results, ids);

		return results;
	}

	private ArrayList<TModel> sortByIds(ArrayList<TModel> results, List<Integer> ids) {
		HashMap<Integer, TModel> map = new HashMap<Integer, TModel>();
		for (TModel model : results) {
			map.put(model.getId(), model);
		}
		ArrayList<TModel> output = new ArrayList<TModel>(results.size());
		for (Integer id : ids) {
			TModel model = map.get(id);
			if (model != null) {
				output.add(map.get(id));
			}
		}
		return output;
	}

	private SelectQuery IdentifiersToQuery(List<Integer> ids) {
		List<String> stringIds = new ArrayList<String>(ids.size());
		for (Integer id : ids) {
			stringIds.add(Integer.toString(id));
		}

		SelectQuery query = new SelectQuery(getTable());
		query.addCondition(new WhereInQuery("id", stringIds));
		return query;
	}

	public abstract String getTable();

	public abstract IQuery SpecificationsToQuery(ISpecification specifications);

	public abstract TModel ReadFromDb(IDatabaseResult dbResult) throws DatabaseException;

	public abstract IQuery ModelToQuery(TModel model);
}
