package net.ypetit.fuse4j.hadoopfs;

import java.nio.ByteBuffer;

import fuse4j.hadoopfs.HdfsClient;
import fuse4j.hadoopfs.HdfsDirEntry;
import fuse4j.hadoopfs.HdfsFileAttr;

public class HdfsClientMockImpl implements HdfsClient {

    public HdfsFileAttr getFileInfo(final String path) {
        // TODO Auto-generated method stub
        return null;
    }

    public HdfsDirEntry[] listPaths(final String path) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object openForRead(final String path) {
        // TODO Auto-generated method stub
        return null;
    }

    public Object createForWrite(final String path) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean close(final Object hdfsFile) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean read(final Object hdfsFile, final ByteBuffer buf,
            final long offset) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean write(final Object hdfsFile, final ByteBuffer buf,
            final long offset) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean mkdir(final String path) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean unlink(final String filePath) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean rmdir(final String dirPath) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean rename(final String src, final String dst) {
        // TODO Auto-generated method stub
        return false;
    }

}
