package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ex2.entity.Memo;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    void bean() {
        assertThat(memoRepository).isNotNull();
        assertThat(memoRepository.getClass().getName()).contains("Proxy");
    }

    @Test
    void create() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            //given
            Memo memo = Memo.builder().memoText("Sample..."+i).build();

            //when
            memoRepository.save(memo);

            //then
        });
    }

    @Test
    void read() {
        //given
        Long mno = 100L;

        //when
        Optional<Memo> result = memoRepository.findById(mno);

        //then
        Memo memo = result.get();
        assertThat(memo).isNotNull();
        assertThat(memo.getMno()).isEqualTo(mno);
    }

    @Test
    void update() {
        //given
        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();

        //when
        Memo result = memoRepository.save(memo);

        //then
        assertThat(memo.getMemoText()).isEqualTo(result.getMemoText());
    }

    @Test
    void delete() {
        //given
        Long mno = 100L;

        //when
        memoRepository.deleteById(mno);

        //then
        memoRepository.findById(mno);
    }
}