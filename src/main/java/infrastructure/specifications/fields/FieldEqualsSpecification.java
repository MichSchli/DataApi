package infrastructure.specifications.fields;

import java.util.List;

import database.query.IQuery;
import database.query.condition.WhereInQuery;
import infrastructure.specifications.ISpecification;

public class FieldEqualsSpecification implements ISpecification {

	public String field;
	public List<String> values;
	
	public FieldEqualsSpecification(String field) {
		this.field = field;
	}
	
	@Override
	public String toString() {
		return "Field ("+field+") in "+values;
	}
	
}
