package infrastructure.specifications.logic;

import infrastructure.specifications.ISpecification;

public class AndSpecification implements ISpecification {

	ISpecification left;
	ISpecification right;

	public AndSpecification(ISpecification left, ISpecification right) {
		this.left = left;
		this.right = right;
	}
}
