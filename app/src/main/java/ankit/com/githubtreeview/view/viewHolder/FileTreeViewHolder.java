package ankit.com.githubtreeview.view.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import ankit.com.githubtreeview.R;
import ankit.com.githubtreeview.model.FileBean;


public class FileTreeViewHolder extends RecyclerView.ViewHolder {
    public ImageView iconIv;
    public TextView nameTv;

    public FileTreeViewHolder(final View itemView, View.OnLongClickListener onLongClickListener) {
        super(itemView);
        iconIv = (ImageView) itemView.findViewById(R.id.icon_iv);
        nameTv = (TextView) itemView.findViewById(R.id.name_tv);

        itemView.setOnLongClickListener(onLongClickListener);

    }

    public void bindData(FileBean file, boolean isLeaf, boolean isExpend, int level) {
        itemView.setTag(file);
        // icon
        if (isLeaf ) {
            iconIv.setVisibility(View.INVISIBLE);
        } else {
            iconIv.setVisibility(View.VISIBLE);
            if ( isExpend) {
                iconIv.setSelected(true);
            } else {
                iconIv.setSelected(false);
            }
        }
        // indent
        itemView.setPadding(level * 40, 0, 0, 0);
        // name
        nameTv.setText(file.fileName);
    }


}
