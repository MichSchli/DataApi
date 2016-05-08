package infrastructure.map;

import java.util.List;

import infrastructure.IModel;

public interface IDataMapper<TModel extends IModel> {

	void doMap(List<TModel> models);
}
