package org.zerock.ex2.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.ex2.entity.Memo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    private final List<Memo> memos = new ArrayList<>();

    @Test
    void bean() {
        assertThat(memoRepository).isNotNull();
        assertThat(memoRepository.getClass().getName()).contains("Proxy");
    }

    @Test
    void create() {
        //given
        Memo add1 = Memo.builder().memoText("Sample").build();
        Memo add2 = Memo.builder().memoText("Sample").build();

        //when
        memoRepository.save(add1);
        memoRepository.save(add2);

        //then
        Memo get1 = memoRepository.findById(add1.getMno()).orElseThrow(RuntimeException::new);
        Memo get2 = memoRepository.findById(add2.getMno()).orElseThrow(RuntimeException::new);
        assertThat(add1).isEqualTo(get1);
        assertThat(add2).isEqualTo(get2);
    }

    @Test
    void duplicate() {
        //given
        Memo add = Memo.builder().memoText("Sample").build();
        memoRepository.save(add);

        //when
        memoRepository.save(add);

        //then

    }

    @Test
    void read() {
        //given
        Memo add = memoRepository.save(Memo.builder().memoText("Sample").build());
        Long mno = add.getMno();

        //when
        Memo get = memoRepository.findById(mno).orElseThrow(RuntimeException::new);

        //then
        assertThat(add).isEqualTo(get);
    }

    @Test
    void update() {
        //given
        Memo add = Memo.builder().memoText("Sample").build();
        Memo modify = Memo.builder().mno(add.getMno()).memoText("Update").build();

        //when
        Memo result = memoRepository.save(modify);

        //then
        assertThat(modify).isEqualTo(result);
    }

    @Test
    void delete() {
        //given
        Memo add = memoRepository.save(Memo.builder().memoText("Sample").build());
        Long mno = add.getMno();

        //when
        memoRepository.deleteById(mno);

        //then
        Optional<Memo> get = memoRepository.findById(mno);
        assertThat(get.isEmpty()).isTrue();
    }

    @Test
    void testPagingLeaning() {
        //given
        save(1, 100);
        int page = 0;
        int size = 10;

        //when
        Pageable pageable = PageRequest.of(page, size);
        Page<Memo> result = memoRepository.findAll(pageable);

        //then
        assertThat(result.getTotalPages()).isEqualTo(10);
        assertThat(result.getTotalElements()).isEqualTo(100);
        assertThat(result.getNumber()).isEqualTo(0);
        assertThat(result.getSize()).isEqualTo(10);
        assertThat(result.hasNext()).isTrue();
        assertThat(result.isFirst()).isTrue();
        assertThat(result.getContent().get(0).getMemoText()).contains("Sample...");
    }

    @Test
    void testPagingSortLeaning() {
        //given
        save(1, 100);
        int page = 0;
        int size = 10;
        Sort descending = Sort.by("mno").descending();
//        Sort ascending = Sort.by("mno").ascending();
//        Sort and = descending.and(ascending);

        //when
        Pageable pageable = PageRequest.of(page, size, descending);
        Page<Memo> result = memoRepository.findAll(pageable);

        //then
        result.get().forEach(System.out::println);
    }

    @Test
    void testQueryMethodsLeaning() {
        //given
        save(1, 100);

        Long from = memos.get(70-1).getMno();
        Long to = memos.get(80-1).getMno();

        //when
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(from ,to);

        //then
        for(Memo memo : list) {
            System.out.println(memo);
        }

        int page = 0;
        int size = 10;
        Sort descending = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(page, size, descending);
        Page<Memo> result = memoRepository.findByMnoBetween(from, to, pageable);

        result.get().forEach(System.out::println);
    }

    @Commit
    @Transactional
    @Test
    void testDeleteQueryMethodsLeaning() {
        save(0, 10);
        memoRepository.deleteMemoByMnoLessThan(10L);
    }

    private void save(int start, int end) {
        IntStream.rangeClosed(start, end).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            Memo result = memoRepository.save(memo);
            memos.add(result);
        });
    }
}