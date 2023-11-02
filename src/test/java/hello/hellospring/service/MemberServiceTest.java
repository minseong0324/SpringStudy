package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemberServiceTest {

    MemberService memberService;

    //여기도 new를 통해 새로운 객체를 생성하는 코드를 이렇게 수정한다.

    MemoryMemberRepository memberRepository;

    //이렇게 하면 테스트를 실행할 때마다 각각 생성해준다.
    //테스트는 독립적으로 실행이 되어야 되기 때문에 각 테스트를 실행하기 전에
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository(); //MemoryMemberRepository 레포지토리를 만들고,
        memberService = new MemberService(memberRepository); // 이를 MemberService에다가 넣어주면,
        //같은 메모리 멤버 레포지토리가 사용이 되는 것이다.
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore(); // 이렇게 하면 돌 때마다 메모리가 clear 된다.
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    //예외처리
    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

       /* try {
            memberService.join(member2);
            fail("예외가 발생해야 합니다.");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123123");
        }*/
        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}