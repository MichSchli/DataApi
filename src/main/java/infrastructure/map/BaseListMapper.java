package infrastructure.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import database.DatabaseException;
import database.IDatabase;
import database.IDatabaseResult;
import database.query.SelectQuery;
import database.query.condition.WhereInQuery;
import infrastructure.IModel;

public abstract class BaseListMapper<TModel extends IModel> implements IDataMapper<TModel> {

	private IDatabase database;

	public BaseListMapper(IDatabase database) {
		this.database = database;
	}
	
	public void doMap(List<TModel> models) {
		if (!models.isEmpty()){
			IDatabaseResult maps = getMapsFromDatabase(models);
			addMapsToModels(models, maps);
		}
	}

	private IDatabaseResult getMapsFromDatabase(List<TModel> models) {
		SelectQuery query = getQuery(models);		
		IDatabaseResult maps = database.executeQuery(query);
		return maps;
	}

	private void addMapsToModels(List<TModel> models, IDatabaseResult maps) {
		HashMap<Integer, TModel> idToModelMap = new HashMap<Integer, TModel>();
		
		for (TModel model : models) {
			idToModelMap.put(model.getId(), model);
		}
		
		try {
			while(maps.next()){
				int modelid = maps.getInt(getFromIdColumn());
				int foreignid = maps.getInt(getToIdColumn());
								
				getIdListOnModel(idToModelMap.get(modelid)).add(foreignid);
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	
	public abstract ArrayList<Integer> getIdListOnModel(TModel model);
	public abstract String getDbMap();
	public abstract String getFromIdColumn();
	public abstract String getToIdColumn();
	
	private SelectQuery getQuery(List<TModel> models){
		SelectQuery query = new SelectQuery(getDbMap());
		ArrayList<String> ids = new ArrayList<String>();
		
		for (TModel model : models) {
			ids.add(Integer.toString(model.getId()));
		}
		
		WhereInQuery where = new WhereInQuery(getFromIdColumn(), ids);
		query.addCondition(where);
		
		return query;
	}
}
