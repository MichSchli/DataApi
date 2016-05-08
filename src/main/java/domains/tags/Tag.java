package domains.tags;

import Serialization.ISerializable;
import infrastructure.IModel;

public class Tag implements IModel{
	public int id;
	public String name;
	
	@Override
	public int getId(){
		return id;
	}

	@Override
	public Tag buildClone() {
		Tag t = new Tag();
		t.id = id;
		t.name = name;
		return t;
	}

}
