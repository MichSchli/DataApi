package domains.images;

import java.util.ArrayList;
import java.util.stream.Collectors;

import Serialization.ISerializable;
import domains.tags.Tag;
import infrastructure.IModel;

public class Image implements IModel{

	int id;
	ArrayList<Integer> tagids;
	ArrayList<Tag> tags;
	String path;
	
	public Image() {
		tagids = new ArrayList<Integer>();
		tags = new ArrayList<Tag>();
	}
			
	@Override
	public int getId(){
		return id;
	}

	@Override
	public Image buildClone() {
		Image i = new Image();
		i.id = this.id;
		i.path = this.path;
		
		for (Integer integer : tagids) {
			i.tagids.add(new Integer(integer.intValue()));
		}
		
		for (Tag tag : tags) {
			i.tags.add(tag.buildClone());
		}
		return i;
	}
}
