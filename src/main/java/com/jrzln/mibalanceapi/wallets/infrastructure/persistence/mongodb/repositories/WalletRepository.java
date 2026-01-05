package com.jrzln.mibalanceapi.wallets.infrastructure.persistence.mongodb.repositories;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;
import com.jrzln.mibalanceapi.wallets.domain.model.aggregates.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Wallet entities in MongoDB.
 */
@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {

    /**
     * Method to retrieve the wallets by user ID.
     *
     * @param userId the ID of the user
     * @return a list of Wallets associated with the given user ID or an empty list if none found
     */
    List<Wallet> findByUserId(UserId userId);
}