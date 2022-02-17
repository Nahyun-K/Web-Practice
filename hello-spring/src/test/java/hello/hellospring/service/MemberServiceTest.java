package hello.hellospring.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import hello.hellospring.domain.Member;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import hello.hellospring.repository.MemoryMemberRepository;
import net.bytebuddy.pool.TypePool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() { // DI
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() { // Test는 과감하게 한글로 바꿔도 됩니다
        // given
        Member member = new Member();
        member.setName("hello");
        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    /**
     * 회원가입() 메소드는 반쪽짜리 test
     * 정상 플로우도 중요하지만
     * 예외 플로우도 훨씬 중요함
     * 중복 회원 검증으로 예외를 찾아야 함
     */
    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring"); // 현재 이름이 중보되고 있음

        // when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // Ctrl + Alt + v
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //assertThrows(NullPointerException.class, () -> memberService.join(member2));

        /*
        memberService.join(member1);
        try {
            memberService.join(member2);
            fail("");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.w");
        }
        */

        // then

    }
    @Test
    void findMember() {
    }

    @Test
    void findOne() {
    }
}