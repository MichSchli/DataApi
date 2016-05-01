package domains.images;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import database.query.SelectQuery;
import database.query.condition.WhereInQuery;
import domains.tags.TagRepository;
import domains.tags.Tag;
import infrastructure.specifications.ISpecification;
import infrastructure.specifications.fields.FieldEqualsSpecification;
import infrastructure.specifications.logic.AndSpecification;
import infrastructure.specifications.logic.OrSpecification;

public class ImageFillHelper {

	private TagRepository tagR;

	public ImageFillHelper(TagRepository tags){
		this.tagR = tags;
	}
	
	public void fillResults(ArrayList<Image> results) {
		ISpecification spec = getSpecification(results);
		List<Tag> tags = tagR.Retrieve(spec);
		
		for (Image image : results) {
			for (Tag tag : tags) {
				System.out.println(image.tagids+" - "+tag.id);
				if (image.tagids.contains(tag.id)){
					image.tags.add(tag);
				}
			}
		}
	}
	
	private ISpecification getSpecification(ArrayList<Image> results){
		FieldEqualsSpecification fspec = new FieldEqualsSpecification("id");
		
		HashSet<String> set = new HashSet<String>();

		for (Image image : results) {
			for (Integer id : image.tagids) {
				set.add(Integer.toString(id));
			}
		}
		
		fspec.values = new ArrayList<String>(set);
		
		return fspec;
	}
}
