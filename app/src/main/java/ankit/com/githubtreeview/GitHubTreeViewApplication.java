package ankit.com.githubtreeview;

import android.app.Application;

/**
 * Created by ankit
 */
public class GitHubTreeViewApplication extends Application {
    private static Application instance;
    public static Application getInstance(){return instance;}

    private static final String TAG = GitHubTreeViewApplication.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


}
