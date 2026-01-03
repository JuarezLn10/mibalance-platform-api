package com.jrzln.mibalanceapi.shared.infrastructure.persistence.mongodb.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * The base repository for MongoDB entities, providing CRUD and pagination operations.
 *
 * @param <T> the type of the entity
 * @param <ID> the type of the entity's identifier
 */
public interface MongoRepository<T, ID> extends PagingAndSortingRepository<T, ID> {}