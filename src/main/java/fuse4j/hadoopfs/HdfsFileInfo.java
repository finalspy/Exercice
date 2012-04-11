package fuse4j.hadoopfs;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

import fuse.FuseFtypeConstants;

public abstract class HdfsFileInfo {
    public boolean directory;

    public long inode;

    private int mode;

    public HdfsFileInfo() {
    }

    public HdfsFileInfo(final boolean directory, final long inode,
            final int mode) {
        this.directory = directory;
        this.inode = inode;
        this.mode = mode;
    }

    public int getMode() {
        return this.mode
                | (this.directory ? FuseFtypeConstants.TYPE_DIR
                        : FuseFtypeConstants.TYPE_FILE);
    }
}
