package net.ypetit.fuse4j.hadoopfj;

import java.nio.ByteBuffer;

import fuse4j.hadoopfs.HdfsClient;
import fuse4j.hadoopfs.HdfsDirEntry;
import fuse4j.hadoopfs.HdfsFileAttr;

public class HdfsClientMockImpl implements HdfsClient {

    public HdfsFileAttr getFileInfo(String path) {
        // TODO Auto-generated method stub
        return null;
    }

    public HdfsDirEntry[] listPaths(String path) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object openForRead(String path) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object createForWrite(String path) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean close(Object hdfsFile) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean read(Object hdfsFile, ByteBuffer buf, long offset) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean write(Object hdfsFile, ByteBuffer buf, long offset) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean mkdir(String path) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean unlink(String filePath) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean rmdir(String dirPath) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean rename(String src, String dst) {
        // TODO Auto-generated method stub
        return false;
    }

}
