package ankit.com.githubtreeview.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import ankit.com.githubtreeview.BuildConfig;
import ankit.com.githubtreeview.model.Items;
import ankit.com.githubtreeview.model.Repo;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by ankit
 */
public class NetworkManager extends ApiController {

    ApiProvider apiProvider;

    public NetworkManager(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        apiProvider = retrofit.create(ApiProvider.class);

    }


    public Observable<List<Repo>> loadMainContent(){
        Observable<Response<List<Repo>>> callTags = apiProvider.getMainContents();
        return handleApiObservable(callTags);
    }


    public Observable<List<Repo>> loadNextUrlContent(String nextUrl ){
        Observable<Response<List<Repo>>> unAnsweredQuestionscall = apiProvider.loadNextDir(nextUrl);
        return handleApiObservable(unAnsweredQuestionscall);
    }

}
