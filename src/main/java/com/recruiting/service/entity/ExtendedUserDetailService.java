package com.recruiting.service.entity;

import com.recruiting.domain.Authority;
import com.recruiting.domain.PasswordResetToken;
import com.recruiting.domain.User;
import com.recruiting.domain.VerificationToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by Martha on 5/14/2017.
 */
public interface ExtendedUserDetailService extends UserDetailsService {

    User findUserByUsername(String username);

    Authority findByRole(String role);

    User save(User user);

    User approveUser(Long id);

    VerificationToken getVerificationToken(String token);

    VerificationToken saveVerificationToken(VerificationToken verificationToken);

    PasswordResetToken getPasswordResetToken(String token);

    void savePasswordResetToken(PasswordResetToken passwordResetToken);

    Page<User> FindAllUsersExceptAdmin(Pageable pageable);

    User findById(Long id);
}
