package ankit.com.githubtreeview.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ankit.com.githubtreeview.R;
import ankit.com.githubtreeview.model.FileBean;
import ankit.com.githubtreeview.model.Repo;
import ankit.com.githubtreeview.network.NetworkManager;
import ankit.com.githubtreeview.view.adapter.FileTreeAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.rvTree)
    RecyclerView rvTree;
    private FileTreeAdapter mTreeAdapter;
    private List<FileBean> mDatas = new ArrayList<>();
    private NetworkManager networkmanager;
    private CompositeSubscription compositeSubscription;
    private List<String> dirsCalled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        dirsCalled = new ArrayList<>();
        compositeSubscription = new CompositeSubscription();
        networkmanager = new NetworkManager();
        compositeSubscription.add(networkmanager.loadMainContent().subscribe(new Observer<List<Repo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError ", e.getLocalizedMessage());
            }

            @Override
            public void onNext(List<Repo> Files) {
                Toast.makeText(MainActivity.this, "onSuccess +" + Files.get(1).getName(), Toast.LENGTH_SHORT).show();
                Log.d("onNext call", Files.toString());
                setNode(Files);
            }
        }));
    }

    @NonNull
    private void setNode(List<Repo> Files) {
        rvTree.setLayoutManager(new LinearLayoutManager(this));

        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final FileBean file = (FileBean) v.getTag();
                final FileBean fileBean = mDatas.get(mDatas.indexOf(file));
                final String typeName = file.getRepoInfo().getType();
                Log.d("dir file Type for next ", typeName);
                if ("dir".equalsIgnoreCase(typeName) && !dirsCalled.contains(file.getRepoInfo().getName())) {
                    dirsCalled.add(file.getRepoInfo().getName());
                    onNextUrl(file, fileBean.getRepoInfo().getUrl());
                }
                return false;
            }
        };

        Files.forEach(item -> {
            if (item.getType().equals("dir")) {
                mDatas.add(new FileBean(mDatas.size() + 1, -1, item.getName(), item));
            } else if (item.getType().equals("file")) {
                mDatas.add(new FileBean(mDatas.size() + 1, -1, item.getName(), item));
            } else {
                throw new RuntimeException("Different Type detected " + item.getType());
            }
        });

        try {
            mTreeAdapter = new FileTreeAdapter(this, mDatas, 1, onLongClickListener);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        rvTree.setAdapter(mTreeAdapter);

    }

    private void onNextUrl(FileBean file, String nextUrl) {
        compositeSubscription.add(networkmanager.loadNextUrlContent(nextUrl).subscribe(new Observer<List<Repo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("onErrorOfNextUrl ", e.getLocalizedMessage());
            }

            @Override
            public void onNext(List<Repo> Files) {
                Log.d("onNext call", Files.toString());
                try {
                    getData(file, Files);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

    private void getData(FileBean file, List<Repo> Files) throws IllegalAccessException {
        Files.forEach(item -> {
            FileBean newFile;
            switch (item.getType()) {
                case "dir":
                    newFile = new FileBean((int) System.currentTimeMillis(), file.fileId, item.getName(), item);
                    break;
                case "file":
                    newFile = new FileBean((int) System.currentTimeMillis(), file.fileId, item.getName(), item);
                    break;
                default:
                    throw new RuntimeException("Different Type detected " + item.getType());
            }
            try {
                mTreeAdapter.addData(newFile);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (compositeSubscription != null && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

}
