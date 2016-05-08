package infrastructure.repositories;

import java.util.List;

import infrastructure.IModel;
import infrastructure.specifications.ISpecification;

public interface IRepository<TModel extends IModel, TSpecification extends ISpecification> {


	List<Integer> Search(TSpecification specification);
	List<TModel> Retrieve(List<Integer> ids);
	void Add(TModel model);
}
