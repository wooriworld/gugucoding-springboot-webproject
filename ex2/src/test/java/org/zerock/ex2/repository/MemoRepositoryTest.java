package org.zerock.ex2.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.ex2.entity.Memo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    private List<Memo> memos = new ArrayList<>();

    @BeforeEach
    void setup() {
        IntStream.rangeClosed(1, 3).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            Memo result = memoRepository.save(memo);
            memos.add(result);
        });
    }

    @AfterEach
    void teardown() {
        memoRepository.deleteAll();
    }

    @Test
    void bean() {
        assertThat(memoRepository).isNotNull();
        assertThat(memoRepository.getClass().getName()).contains("Proxy");
    }

    @Test
    void create() {
        Memo memo = Memo.builder().memoText("Sample").build();
        memoRepository.save(memo);
    }

    @Test
    void read() {
        //given
        Long mno = memos.get(0).getMno();

        //when
        Optional<Memo> result = memoRepository.findById(mno);

        //then
        Memo get = result.get();
        assertThat(get.getMno()).isEqualTo(mno);
    }

    @Test
    void update() {
        //given
        Memo memo = memos.get(0);

        //when
        Memo result = memoRepository.save(memo);

        //then
        assertThat(memo.getMemoText()).isEqualTo(result.getMemoText());
    }

    @Test
    void delete() {
        //given
        Long mno = memos.get(0).getMno();

        //when
        memoRepository.deleteById(mno);

        //then
        Optional<Memo> memo = memoRepository.findById(mno);
        assertThat(memo.isEmpty()).isTrue();
    }
}