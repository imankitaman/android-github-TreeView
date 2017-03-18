package ankit.com.githubtreeview.network;

import java.net.HttpRetryException;

import ankit.com.githubtreeview.model.Items;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ankit
 */
public class ApiController {

    protected <T> Observable<T> handleApiObservable(Observable<Response<T>> t) {
        return t.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(this::handleHttpError)
                .filter(responseServerResponse -> responseServerResponse.isSuccessful() && responseServerResponse.body() != null)
                .map(serverResponseResponse -> serverResponseResponse.body());
    }

    private <T> Observable<Response<T>> handleHttpError(Throwable throwable) throws RuntimeException {
        //TODO : create proper custom exceptions later.

        if (throwable instanceof HttpException) {
            int status = ((HttpRetryException) throwable).responseCode();
            if (status >= 400 && status < 500)
                throw new RuntimeException("Url Not Found. Status : " + status);
            else
                throw new RuntimeException("Server runtime exception");
        } else {
            throw new RuntimeException("Something went wrong." + throwable.toString());
        }

    }

}
