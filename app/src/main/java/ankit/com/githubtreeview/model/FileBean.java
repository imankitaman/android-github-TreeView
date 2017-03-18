package ankit.com.githubtreeview.model;


import ankit.com.githubtreeview.uiutils.annotation.NodeId;
import ankit.com.githubtreeview.uiutils.annotation.NodePid;

public class FileBean {

    @NodeId
    public int fileId;
    @NodePid
    public int filePId;

    public String fileName;
    public Repo repoInfo;

    public FileBean(int fileId, int filePId, String fileName, Repo repo) {
        this.fileId = fileId;
        this.filePId = filePId;
        this.fileName = fileName;
        this.repoInfo = repo;
    }


    public Repo getRepoInfo() {
        return repoInfo;
    }
}
