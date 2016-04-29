package infrastructure;

import database.query.IQuery;
import database.query.SelectQuery;
import database.query.condition.WhereInQuery;
import domains.Domain;
import infrastructure.specifications.ISpecification;
import infrastructure.specifications.fields.FieldEqualsSpecification;

public class SpecificationHelper {

	public static IQuery SpecificationToQuery(ISpecification specification, String table){
		SelectQuery query = new SelectQuery(table);
		
		AddSpecificationToQuery(query, specification);
		
		return query;
	}
	
	private static void AddSpecificationToQuery(SelectQuery query, ISpecification specification){
		if (specification.getClass().getName().contains("FieldEquals")){
			FieldEqualsSpecification spec = (FieldEqualsSpecification) specification;
			query.addCondition(new WhereInQuery(spec.field, spec.values));
		}
	}
}
