package infrastructure.repositories;

import java.util.List;

import infrastructure.IModel;
import infrastructure.specifications.ISpecification;

public interface IRepository<TModel extends IModel> {

	List<TModel> Retrieve(ISpecification specifications);
	void Add(TModel model);
}
