package com.project.planb.security;

import com.project.planb.entity.Member;
import com.project.planb.exception.CustomException;
import com.project.planb.exception.ErrorCode;
import com.project.planb.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    // Security에서 사용할 PrincipalDetails를 반환하는 서비스 - 'account'로 유효성 검사
    private MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        Member member = memberRepository.findByAccount(account)
                .orElseThrow(() -> {
                    return new CustomException(ErrorCode.USER_NOT_FOUND);
                });
        return new PrincipalDetails(member);
    }

}
