package domains.images;

import java.util.ArrayList;
import java.util.List;

import infrastructure.specifications.ISpecification;

public class ImageSpecification implements ISpecification{
	private List<Integer> ids;
	public ImageSpecification() {
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
