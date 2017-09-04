package com.recruiting.repository;

import com.recruiting.domain.PasswordResetToken;

/**
 * Created by Martha on 6/28/2017.
 */
public interface PasswordResetTokenRepository extends BaseRepository<PasswordResetToken> {

    PasswordResetToken findPasswordResetTokenByToken(String token);
}
