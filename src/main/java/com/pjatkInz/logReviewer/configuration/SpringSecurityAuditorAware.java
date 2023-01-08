package com.pjatkInz.logReviewer.configuration;

import com.pjatkInz.logReviewer.model.MyUser;
import com.pjatkInz.logReviewer.model.MyUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(MyUserDetails.class::cast)
                .map(MyUserDetails::getEmploeeID);
    }

//    @Override
//    public Optional<MyUserDetails> getCurrentAuditor() {
//
//        return Optional.ofNullable(SecurityContextHolder.getContext())
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .map(Authentication::getPrincipal)
//                .map(User.class::cast);
//    }
}
