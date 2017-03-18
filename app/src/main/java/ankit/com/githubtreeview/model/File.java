package ankit.com.githubtreeview.model;


import ankit.com.githubtreeview.R;
import ankit.com.githubtreeview.uiutils.LayoutItemType;

public class File implements LayoutItemType {
    public String fileName;

    public File(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_file;
    }
}
