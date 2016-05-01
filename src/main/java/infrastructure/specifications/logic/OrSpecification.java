package infrastructure.specifications.logic;

import infrastructure.specifications.ISpecification;

public class OrSpecification implements ISpecification {

	public ISpecification left;
	public ISpecification right;

	public OrSpecification(ISpecification left, ISpecification right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String toString() {
		return "( "+left + " OR " + right + " )";
	}
}
