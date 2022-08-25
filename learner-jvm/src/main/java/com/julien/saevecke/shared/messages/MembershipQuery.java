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
    // uuid identifies the MembershipQuery (set by publisher)
    UUID uuid;
    // contains pod name that answered the query (set by consumer)
    String podName;
    // artificial processing delay in seconds (set by publisher)
    int delayInSeconds;
    // query represents the query itself (set by publisher, modified by consumer)
    DefaultQueryProxy query;
}
