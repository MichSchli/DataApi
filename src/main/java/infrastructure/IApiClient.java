package infrastructure;

import java.util.List;

import api.ApiRequest;
import domains.Domain;

public interface IApiClient {

	List<IModel> Search(ApiRequest request);

	Domain<?, ?> getDomain(String part);
}
