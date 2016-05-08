package infrastructure.fill;

import java.util.List;

import infrastructure.IModel;

public interface IDataFiller<TModel extends IModel> {

	void doFill(List<TModel> models) throws FillException;
}
