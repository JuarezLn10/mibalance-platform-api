package com.jrzln.mibalanceapi.shared.domain.model.aggregates;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

/**
 * Base class for auditable documents in MongoDB.
 */
@Getter
@Setter
public abstract class AuditableDocument {

    // The unique identifier for the document
    @Id
    private String id;

    // The timestamp when the document was created
    @CreatedDate
    @Field("createdAt")
    private Instant createdAt;

    // The timestamp when the document was last modified
    @LastModifiedDate
    @Field("updatedAt")
    private Instant updatedAt;
}
