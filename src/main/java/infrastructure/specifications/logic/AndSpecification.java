package infrastructure.specifications.logic;

import infrastructure.specifications.ISpecification;

public class AndSpecification implements ISpecification {

	public ISpecification left;
	public ISpecification right;

	public AndSpecification(ISpecification left, ISpecification right) {
		this.left = left;
		this.right = right;
	}
	
	@Override
	public String toString() {
		return "( "+left + " AND " + right + " )";
	}
}
