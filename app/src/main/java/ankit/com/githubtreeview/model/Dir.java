package ankit.com.githubtreeview.model;


import ankit.com.githubtreeview.R;
import ankit.com.githubtreeview.uiutils.LayoutItemType;

public class Dir implements LayoutItemType {
    public String dirName;

    public Dir(String dirName) {
        this.dirName = dirName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_dir;
    }
}
