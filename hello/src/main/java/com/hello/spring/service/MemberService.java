package com.hello.spring.service;

import java.util.List;
import java.util.Optional;

import com.hello.spring.domain.Member;
import com.hello.spring.repository.MemberRepository;
import com.hello.spring.repository.MemoryMemberRepository;

public class MemberService {
	
	private final MemberRepository memberRepository = new MemoryMemberRepository();
	
	/*회원가입*/
	public Long join(Member member) {
		//같은 이름이 있는 중복 회원은 안된다.
		validateDuplicateMember(member); //중복회원검증
		memberRepository.save(member);
		return member.getId();
		
	}

	private void validateDuplicateMember(Member member) {
		memberRepository.findByName(member.getName())
		.ifPresent(m -> {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		});
	}
	
	/*전체 회원 조회*/
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	public Optional<Member> findOne(Long memberId) {
		return memberRepository.findById(memberId);
	}
}
