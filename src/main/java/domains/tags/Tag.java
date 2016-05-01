package domains.tags;

import infrastructure.IModel;

public class Tag implements IModel {
	public int id;
	public String name;
	
	@Override
	public String Serialize() {
		return "{\n"
				+ "\"id\":"+id+",\n"
				+ "\"name\":\""+name+"\"\n"
						+ "}";
	}

	@Override
	public int getId(){
		return id;
	}

}
