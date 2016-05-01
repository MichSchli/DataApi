package domains.images;

import java.util.ArrayList;
import java.util.stream.Collectors;

import domains.tags.Tag;
import infrastructure.IModel;

public class Image implements IModel {

	int id;
	ArrayList<Integer> tagids;
	ArrayList<Tag> tags;
	String path;
	
	public Image() {
		tagids = new ArrayList<Integer>();
		tags = new ArrayList<Tag>();
	}
	
	@Override
	public String Serialize() {
		return "{\n"
				+ "\"id\":"+id+",\n"
				+ "\"path\":\""+path+"\",\n"
				+ "\"tagids\":"+tagids+",\n"
				+ "\"tags\":"+SerializeTags()+"\n"
				+ "}";
	}
	
	public String SerializeTags(){
		String s = "[\n";
		
		s+= tags.stream().map(t -> t.Serialize()).collect(Collectors.joining(",\n"));
		
		s += "\n]";
		return s;
	}
	
	@Override
	public int getId(){
		return id;
	}
}
