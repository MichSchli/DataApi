package database.query.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import database.query.IQuery;

public class WhereInQuery implements IDbCondition {

	private String field;
	private ArrayList<String> values;

	public WhereInQuery(String field, List<String> values) {
		this.field = field;
		this.values = new ArrayList<String>();
		this.values.addAll(values);
	}

	public String process() {
		return field+" in ("+values.stream().map(v -> "'"+v+"'").collect(Collectors.joining(","))+")";
	}

}
