package domains;

import java.util.HashMap;

import infrastructure.IModel;
import infrastructure.repositories.IRepository;
import infrastructure.specifications.ISpecification;

public abstract class Domain<TModel extends IModel, TSpecification extends ISpecification> {

	public abstract TSpecification getNewSpecification();
	public abstract IRepository<TModel, TSpecification> getRepository();
	public abstract void addToSpecifications(TSpecification specifications, HashMap<String, String> queryHashmap);
	
	public TSpecification getNewSpecification(HashMap<String, String> queryHashmap){
		TSpecification spec = getNewSpecification();
		addToSpecifications(spec, queryHashmap);
		
		return spec;
	}
}
