package ankit.com.githubtreeview.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import ankit.com.githubtreeview.R;
import ankit.com.githubtreeview.model.FileBean;
import ankit.com.githubtreeview.uiutils.TreeAdapter;
import ankit.com.githubtreeview.view.viewHolder.FileTreeViewHolder;

public class FileTreeAdapter extends TreeAdapter<FileBean> {

    private LayoutInflater mLayoutInflater ;

    private View.OnLongClickListener mOnLongClickListener ;

    public FileTreeAdapter(Context context, List<FileBean> datas, int defaultExpandLevel, View.OnLongClickListener onItemLongClickListener) throws IllegalAccessException, IllegalArgumentException {
        super(context, datas, defaultExpandLevel);
        mLayoutInflater = LayoutInflater.from(context) ;
        mOnLongClickListener = onItemLongClickListener ;
    }

    @Override
    public FileTreeViewHolder onCreateTreeViewHolder(ViewGroup viewGroup, int i) {
        return new FileTreeViewHolder(mLayoutInflater.inflate(R.layout.item ,viewGroup,false), mOnLongClickListener);
    }

    @Override
    public void onBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, int position, FileBean obj, boolean isLeaf, boolean isExpend, int level) {
        ((FileTreeViewHolder)viewHolder).bindData(obj,isLeaf,isExpend,level) ;
    }


}
