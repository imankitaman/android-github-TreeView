package ankit.com.githubtreeview.view.viewHolder;

import android.view.View;
import android.widget.TextView;

import ankit.com.githubtreeview.R;
import ankit.com.githubtreeview.model.File;
import ankit.com.githubtreeview.uiutils.TreeNode;
import ankit.com.githubtreeview.uiutils.TreeViewBinder;


public class FileNodeBinder extends TreeViewBinder<FileNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        File fileNode = (File) node.getContent();
        holder.tvName.setText(fileNode.fileName);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_file;
    }

    public class ViewHolder extends TreeViewBinder.ViewHolder {
        public TextView tvName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
