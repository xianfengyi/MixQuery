package com.pioneeryi.mixquery.jdbc.model.execute;

/**
 * 查询执行信息.
 */
public class QueryInfo {

    private float progress;
    private long duration;

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return new StringBuffer()
                .append("QueryInfo{")
                .append("progress=")
                .append(progress)
                .append(", duration=")
                .append(duration)
                .append('}')
                .toString();
    }
}
