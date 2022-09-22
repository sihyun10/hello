package com.hello.spring.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.hello.spring.domain.Member;
import com.hello.spring.repository.MemoryMemberRepository;

class MemberServiceTest {
	
	MemberService memberService = new MemberService();
	MemoryMemberRepository memberRepository = new MemoryMemberRepository();
	
	@AfterEach
	public void afterEach() {
		memberRepository.clearStore();
	}
	
	@Test
	void 회원가입() {
		//given
		Member member = new Member();
		member.setName("Spring");
		
		//when
		Long saveId = memberService.join(member);
		
		//then
		Member findMember = memberService.findOne(saveId).get();
		assertThat(member.getName()).isEqualTo(findMember.getName());
	}
	
	@Test
	public void 중복_회원_예외() {
		//given
		Member member1 = new Member();
		member1.setName("Spring");
		
		Member member2 = new Member();
		member2.setName("Spring");
		
		//when
		memberService.join(member1);
		IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
		assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
		
/*
		try {
			memberService.join(member2);
			fail();
		} catch (IllegalStateException e) {
			assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
		}
*/
		
		//then
	}
}
