package com.julien.saevecke.shared.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MembershipQuery implements Serializable {
    private static final long serialVersionUID = 1234635319693213385L;

    public MembershipQuery(UUID uuid, DefaultQueryProxy query) {
        this.uuid = uuid;
        this.query = query;
    }

    // uuid identifies the MembershipQuery (set by publisher)
    private UUID uuid;
    // contains pod name that answered the query (set by consumer)
    private String podName;
    // query represents the query itself (set by publisher, modified by consumer)
    private DefaultQueryProxy query;

    // statistics
    private long podStartUpTime;
    private long podProcessingTime;
    private long podWaitTime;
}
