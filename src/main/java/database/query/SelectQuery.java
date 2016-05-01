package database.query;

import java.util.ArrayList;
import java.util.stream.Collectors;

import database.query.condition.IDbCondition;
import database.query.sort.IDbSort;

public class SelectQuery implements IQuery {

	private ArrayList<String> rows = new ArrayList<String>();
	
	private ArrayList<IDbCondition> conditions = new ArrayList<IDbCondition>();
	public ArrayList<IDbCondition> getConditions() {
		return conditions;
	}

	private ArrayList<IDbSort> sorts = new ArrayList<IDbSort>();
	private String table;
	
	public SelectQuery(String string, String string2) {
		rows.add(string);
		table = string2;
	}

	public SelectQuery(String string) {
		table = string;
	}

	public SelectQuery(ArrayList<String> items, String string2) {
		rows.addAll(items);
		table = string2;
	}

	public String process() {
		String rowString;
		if (rows.isEmpty()){
			rowString = "*";
		}else{
			rowString = String.join(",", rows);
		}
		
		String conditionString;
		if (conditions.isEmpty()){
			conditionString = "";
		}else{
			conditionString = " where "+conditions.stream()
			 .map(IDbCondition::process)
			 .collect(Collectors.joining(" and "));
		}
		
		String sortString;
		if (sorts.isEmpty()){
			sortString = "";
		}else{
			sortString = " order by "+sorts.stream()
			 .map(IDbSort::process)
			 .collect(Collectors.joining(", "));
		}
		
		return "select "+rowString+" from "+table+conditionString+sortString;
	}

	
	
	public void addCondition(IDbCondition c) {
		conditions.add(c);
	}

	public void addSort(IDbSort s) {
		sorts.add(s);
	}

	public String getTable() {
		return table;
	}

}
