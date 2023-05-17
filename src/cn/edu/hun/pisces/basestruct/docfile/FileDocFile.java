package cn.edu.hun.pisces.basestruct.docfile;

import java.io.File;

/**
 * @author: Leilei Du
 * @Date: 2018/7/8 14:07
 */
public class FileDocFile extends DocFile {
    private File file = null;
    private String ind = null;

    public FileDocFile(File file) {
        this.file = file;
//        this.ind = file.getName();
        this.ind = file.getAbsolutePath();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        this.ind = file.getName();
    }

    @Override
    public String getInd() {
        return ind;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FileDocFile){
            FileDocFile fdf = (FileDocFile)obj;
            return this.file.equals(fdf.getFile());
        }
        return false;
    }

    @Override
    public String toString() {
        return "FileDocFile{" +
                "file=" + file +
                ", ind='" + ind + '\'' +
                '}';
    }
}