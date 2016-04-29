package domains.images;

import infrastructure.IModel;

public class Image implements IModel {

	int id;
	String path;
	
	@Override
	public String Serialize() {
		return "{\n"
				+ "\"id\":"+id+",\n"
				+ "\"path\":\""+path+"\"\n"
						+ "}";
	}
}
