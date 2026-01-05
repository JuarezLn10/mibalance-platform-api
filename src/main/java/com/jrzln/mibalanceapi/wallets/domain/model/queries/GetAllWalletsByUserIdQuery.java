package com.jrzln.mibalanceapi.wallets.domain.model.queries;

import com.jrzln.mibalanceapi.shared.domain.model.valueobjects.UserId;

/**
 * Query to retrieve all the wallets by user ID.
 */
public record GetAllWalletsByUserIdQuery(UserId userId) {}