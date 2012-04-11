/**
 * FUSE-J: Java bindings for FUSE (Filesystem in Userspace by Miklos Szeredi
 * (mszeredi@inf.bme.hu)) Copyright (C) 2003 Peter Levart (peter@select-tech.si)
 * This program can be distributed under the terms of the GNU LGPL. See the file
 * COPYING.LIB
 */

package fuse.util;

public abstract class Struct implements Cloneable {
    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (final CloneNotSupportedException e) {
            // will not happen
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(this.getClass().getName());

        return sb.append("[ ")
                .append(this.appendAttributes(sb, false) ? ", " : "")
                .append("hashCode=").append(this.hashCode()).append(" ]")
                .toString();
    }

    protected boolean appendAttributes(final StringBuilder buff,
            final boolean isPrefixed) {
        return isPrefixed;
    }
}
