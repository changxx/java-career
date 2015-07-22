package com.changxx.practice.zookeeper.lock;

/**
 * @author changxx on 15-7-21.
 */
public class LockNode implements Comparable<LockNode> {

    private final String name;

    private String prefix;

    private int sequence = -1;

    public LockNode(String name) {
        this.name = name;
        this.prefix = name;
        int idx = name.indexOf("-");

        if (idx > 0) {
            this.prefix = name.substring(0, idx);
            this.sequence = Integer.parseInt(name.substring(idx + 1));
        }
    }

    @Override
    public int compareTo(LockNode that) {
        int s1 = this.sequence;
        int s2 = that.sequence;

        if (s1 == -1 && s2 == -1) {
            return this.name.compareTo(that.name);
        }

        if (s1 == -1) {
            return -1;
        } else if (s2 == -1) {
            return 1;
        } else {
            return s1 - s2;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LockNode lockNode = (LockNode) o;

        if (!name.equals(lockNode.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
