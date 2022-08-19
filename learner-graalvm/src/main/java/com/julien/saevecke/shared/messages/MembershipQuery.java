package com.julien.saevecke.shared.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
/*
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Component
public class MembershipQuery {
    // uuid identifies the MembershipQuery (set by publisher)
    String uuid;
    // contains pod name that answered the query (set by consumer)
    String podName;
    // artificial processing delay in seconds (set by publisher)
    int delayInSeconds;
    // query represents the query itself (set by publisher, modified by consumer)
    DefaultQueryProxy query;
}*/

public class MembershipQuery implements Serializable {
    private static final long serialVersionUID = 9109635319693213385L;

    @Override
    public String toString() {
        return "MembershipQuery{" +
                "uuid='" + uuid + '\'' +
                ", podName='" + podName + '\'' +
                ", delayInSeconds=" + delayInSeconds +
                ", prefix=" + prefix +
                ", suffix=" + suffix +
                ", output=" + output +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MembershipQuery that = (MembershipQuery) o;
        return delayInSeconds == that.delayInSeconds && Objects.equals(uuid, that.uuid) && Objects.equals(podName, that.podName) && Objects.equals(prefix, that.prefix) && Objects.equals(suffix, that.suffix) && Objects.equals(output, that.output);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, podName, delayInSeconds, prefix, suffix, output);
    }
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPodName() {
        return podName;
    }

    public void setPodName(String podName) {
        this.podName = podName;
    }

    public int getDelayInSeconds() {
        return delayInSeconds;
    }

    public void setDelayInSeconds(int delayInSeconds) {
        this.delayInSeconds = delayInSeconds;
    }

    public ArrayList<String> getPrefix() {
        return prefix;
    }

    public void setPrefix(ArrayList<String> prefix) {
        this.prefix = prefix;
    }

    public ArrayList<String> getSuffix() {
        return suffix;
    }

    public void setSuffix(ArrayList<String> suffix) {
        this.suffix = suffix;
    }

    public ArrayList<String> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<String> output) {
        this.output = output;
    }

    @JsonCreator
    public MembershipQuery() {
        this.uuid = "UNKNOWN";
        this.podName = "UNKNOWN";
        this.delayInSeconds = 0;
        this.prefix = new ArrayList<>();
        this.suffix = new ArrayList<>();
        this.output = new ArrayList<>();
    }

    @JsonCreator
    public MembershipQuery(String uuid, String podName, int delayInSeconds, ArrayList<String> prefix, ArrayList<String> suffix, ArrayList<String> output) {
        this.uuid = uuid;
        this.podName = podName;
        this.delayInSeconds = delayInSeconds;
        this.prefix = prefix;
        this.suffix = suffix;
        this.output = output;
    }

    // uuid identifies the MembershipQuery (set by publisher)
    @JsonProperty("uuid")
    String uuid;
    // contains pod name that answered the query (set by consumer)
    @JsonProperty("pod_name")
    String podName;
    // artificial processing delay in seconds (set by publisher)
    @JsonProperty("delay_in_seconds")
    int delayInSeconds;
    // query represents the query itself (set by publisher, modified by consumer)
    @JsonProperty("prefix")
    ArrayList<String> prefix;
    @JsonProperty("suffix")
    ArrayList<String> suffix;
    @JsonProperty("output")
    ArrayList<String> output;
}
