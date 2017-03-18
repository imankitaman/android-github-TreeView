package ankit.com.githubtreeview.network;

import java.util.List;

import ankit.com.githubtreeview.model.Repo;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by ankit
 */

public interface ApiProvider {


    @GET("repos/imankitaman/stack-overflow-api-master/contents")
    Observable<Response<List<Repo>>> getMainContents();

    //LOAD NEXT DIR
    @GET
    Observable<Response<List<Repo>>> loadNextDir(@Url String url);

}
