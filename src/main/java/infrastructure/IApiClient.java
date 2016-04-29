package infrastructure;

import java.util.List;

import api.ApiRequest;

public interface IApiClient {

	List<IModel> Search(ApiRequest request);
}
