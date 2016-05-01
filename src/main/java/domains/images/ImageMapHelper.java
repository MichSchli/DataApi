package domains.images;

import java.util.ArrayList;

import database.IDatabase;
import infrastructure.map.BaseListMapper;

public class ImageMapHelper extends BaseListMapper<Image> {


	public ImageMapHelper(IDatabase database) {
		super(database);
	}

	@Override
	public ArrayList<Integer> getIdListOnModel(Image model) {
		return model.tagids;
	}

	@Override
	public String getDbMap() {
		return "image2tag";
	}

	@Override
	public String getFromIdColumn() {
		return "imageid";
	}

	@Override
	public String getToIdColumn() {
		return "tagid";
	}
	
}
