package domains.tags;

import java.util.ArrayList;
import java.util.List;

import infrastructure.specifications.ISpecification;

public class TagSpecification implements ISpecification{

	private List<Integer> ids;
	public TagSpecification() {
		ids = new ArrayList<Integer>();
	}
	
	@Override
	public List<Integer> getIds() {
		return ids;
	}

	@Override
	public boolean isIdentifierLookup() {
		return !ids.isEmpty();
	}

	public void addId(int parseInt) {
		ids.add(parseInt);
	}
}
