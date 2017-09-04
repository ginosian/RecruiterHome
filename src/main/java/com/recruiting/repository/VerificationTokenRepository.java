package com.recruiting.repository;


import com.recruiting.domain.VerificationToken;

/**
 * Created by Martha on 6/25/2017.
 */

public interface VerificationTokenRepository extends BaseRepository<VerificationToken> {


    VerificationToken findVerificationTokenByToken(String token);
}
