package infrastructure.fill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import infrastructure.IModel;
import infrastructure.repositories.IRepository;

public abstract class BaseListFiller<TModel extends IModel, TForeign extends IModel> implements IDataFiller<TModel> {

	private IRepository<TForeign,?> repository;

	public BaseListFiller(IRepository<TForeign,?> repository) {
		this.repository = repository;
	}

	public void doFill(List<TModel> models) throws FillException {
		boolean shouldFill = false;
		for (TModel model : models) {
			if (!getIdListOnModel(model).isEmpty()){
				shouldFill = true;
				break;
			}
		}		
		
		if (shouldFill) {
			List<TForeign> foreigns = retrieveFromRepository(models);
			fillFromRetrieved(models, foreigns);
		}
	}

	private List<TForeign> retrieveFromRepository(List<TModel> models) {
		ArrayList<Integer> ids = getIdentifiers(models);
		List<TForeign> foreigns = repository.Retrieve(ids);
		return foreigns;
	}

	@SuppressWarnings("unchecked")
	private void fillFromRetrieved(List<TModel> models, List<TForeign> foreigns) throws FillException {
		HashMap<Integer, TForeign> foreignMap = new HashMap<Integer, TForeign>();
		for (TForeign foreign : foreigns) {
		   foreignMap.put(foreign.getId(), foreign);
		}
		
		for (TModel model : models) {
			for (Integer id : getIdListOnModel(model)) {
				TForeign foreign = foreignMap.get(id);
				if (foreign == null){
					throw new FillException("Mapper contained link to nonexisting resource (id = "+id.toString()+")");
				}
				getForeignListOnModel(model).add((TForeign) foreign.buildClone());
			}
		}
	}

	private ArrayList<Integer> getIdentifiers(List<TModel> models) {
		HashSet<Integer> set = new HashSet<Integer>();

		for (TModel model : models) {
			for (Integer id : getIdListOnModel(model)) {
				set.add(id);
			}
		}

		return new ArrayList<Integer>(set);
	}

	public abstract ArrayList<Integer> getIdListOnModel(TModel model);

	public abstract ArrayList<TForeign> getForeignListOnModel(TModel model);
}
