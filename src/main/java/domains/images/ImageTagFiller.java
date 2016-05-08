package domains.images;

import java.util.ArrayList;
import domains.tags.ITagRepository;
import domains.tags.Tag;
import infrastructure.fill.BaseListFiller;

public class ImageTagFiller extends BaseListFiller<Image, Tag> {

	public ImageTagFiller(ITagRepository repository) {
		super(repository);
	}

	@Override
	public ArrayList<Integer> getIdListOnModel(Image model) {
		return model.tagids;
	}

	@Override
	public ArrayList<Tag> getForeignListOnModel(Image model) {
		return model.tags;
	}
}
