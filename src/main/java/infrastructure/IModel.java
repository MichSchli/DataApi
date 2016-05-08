package infrastructure;

import Serialization.ISerializable;

public interface IModel extends ISerializable{

	int getId();
	IModel buildClone();
}
