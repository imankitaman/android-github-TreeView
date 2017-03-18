package ankit.com.githubtreeview.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ankit.com.githubtreeview.R;
import ankit.com.githubtreeview.model.Dir;
import ankit.com.githubtreeview.model.File;
import ankit.com.githubtreeview.model.Items;
import ankit.com.githubtreeview.model.Repo;
import ankit.com.githubtreeview.network.NetworkManager;
import ankit.com.githubtreeview.uiutils.TreeNode;
import ankit.com.githubtreeview.uiutils.TreeViewAdapter;
import ankit.com.githubtreeview.view.viewHolder.DirectoryNodeBinder;
import ankit.com.githubtreeview.view.viewHolder.FileNodeBinder;
import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.rvTree)
    RecyclerView rvTree;
    private TreeViewAdapter adapter;
    private NetworkManager networkmanager;
    private CompositeSubscription compositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        compositeSubscription = new CompositeSubscription();
//        initData();

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
//                adapter.clearTagItems();
//                adapter.setTagItems(tagItems.getItems());
                setNode(Files);
//                adapter.notifyDataSetChanged();
//                progressDialog.cancel();
            }
        }));
    }

    private void setNode(List<Repo> Files) {
        rvTree.setLayoutManager(new LinearLayoutManager(this));
        List<TreeNode> nodes = getTreeNodes(Files);
        adapter = new TreeViewAdapter(nodes, Arrays.asList(new FileNodeBinder(), new DirectoryNodeBinder()));
        rvTree.setAdapter(adapter);

        adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, Repo repo, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    //Update and toggle the node.
                    onToggle(!node.isExpand(), holder);
//                    if (!node.isExpand())
//                        adapter.collapseBrotherNode(node);
                } else {
                    Toast.makeText(MainActivity.this, repo.getUrl(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
                DirectoryNodeBinder.ViewHolder dirViewHolder = (DirectoryNodeBinder.ViewHolder) holder;
                final ImageView ivArrow = dirViewHolder.getIvArrow();
                int rotateDegree = isExpand ? 90 : -90;
                ivArrow.animate().rotationBy(rotateDegree)
                        .start();
            }
        });
    }

    @NonNull
    private List<TreeNode> getTreeNodes(List<Repo> Files) {
        List<TreeNode> nodes = new ArrayList<>();
        Files.forEach(item -> {
            if (item.getType().equals("dir")) {
                nodes.add(new TreeNode(new Dir(item.getName()), item));
            } else if (item.getType().equals("file")) {
                nodes.add(new TreeNode(new File(item.getName()), item));
            } else {
                throw new RuntimeException("Different Type detected " + item.getType());
            }
        });
        return nodes;
    }


    private void a(String nextUrl) {
        compositeSubscription.add(networkmanager.loadNextUrlContent(nextUrl).subscribe(new Observer<List<Repo>>() {
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
                Files.forEach();
//               adapter.setTreeLeaf(getTreeNodes(Files));
            }
        }));
    }

//    private void initData() {
//        List<TreeNode> nodes = new ArrayList<>();
//        TreeNode<Dir> app = new TreeNode<>(new Dir("app"));
//        nodes.add(app);
//        app.addChild(
//                new TreeNode<>(new Dir("manifests"))
//                        .addChild(new TreeNode<>(new File("AndroidManifest.xml")))
//        );
//        app.addChild(
//                new TreeNode<>(new Dir("java")).addChild(
//                        new TreeNode<>(new Dir("tellh")).addChild(
//                                new TreeNode<>(new Dir("com")).addChild(
//                                        new TreeNode<>(new Dir("recyclertreeview"))
//                                                .addChild(new TreeNode<>(new File("Dir")))
//                                                .addChild(new TreeNode<>(new File("DirectoryNodeBinder")))
//                                                .addChild(new TreeNode<>(new File("File")))
//                                                .addChild(new TreeNode<>(new File("FileNodeBinder")))
//                                                .addChild(new TreeNode<>(new File("TreeViewBinder")))
//                                )
//                        )
//                )
//        );
//
//        TreeNode<Dir> res = new TreeNode<>(new Dir("res"));
//        nodes.add(res);
//        res.addChild(
//                new TreeNode<>(new Dir("layout"))
//                        .addChild(new TreeNode<>(new File("activity_main.xml")))
//                        .addChild(new TreeNode<>(new File("item_dir.xml")))
//                        .addChild(new TreeNode<>(new File("item_file.xml")))
//        );
//        res.addChild(
//                new TreeNode<>(new Dir("mipmap"))
//                        .addChild(new TreeNode<>(new File("ic_launcher.png")))
//        );
//
//        rvTree.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new TreeViewAdapter(nodes, Arrays.asList(new FileNodeBinder(), new DirectoryNodeBinder()));
//        // whether collapse child nodes when their parent node was close.
////        adapter.ifCollapseChildWhileCollapseParent(true);
//
//
//        rvTree.setAdapter(adapter);
//    }

//        private void initRecyclerView() {
//        rvTree.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new TreeViewAdapter(new ArrayList<>(), Arrays.asList(new FileNodeBinder(), new DirectoryNodeBinder()));
//        rvTree.setAdapter(adapter);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (compositeSubscription != null && !compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

}
