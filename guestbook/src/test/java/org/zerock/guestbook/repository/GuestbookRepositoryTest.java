package org.zerock.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.GuestBook;
import org.zerock.guestbook.entity.QGuestBook;

import java.util.Optional;
import java.util.stream.IntStream;


@SpringBootTest
class GuestbookRepositoryTest {
    // TODO 2022.03.06 WOORI 테스트 독립적으로 변경 필요
    // TODO 2022.03.06 WOORI given when then 형태로 변경
    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    void insert() {
        IntStream.rangeClosed(1, 300).forEach(i-> {
            GuestBook guestBook = GuestBook.builder()
                    .title("title..."+i)
                    .content("content..."+i)
                    .writer("user"+(i%10))
                    .build();
            System.out.println(guestbookRepository.save(guestBook));
        });
    }

    @Test
    void update() {
        Optional<GuestBook> result = guestbookRepository.findById(300L);
        GuestBook guestBook = result.get();
        guestBook.changeTitle("Changed Title...");
        guestBook.changeContent("Changed Content...");
        guestbookRepository.save(guestBook);
    }

    @Test
    void testQuerydsl_제목LIKE검색() {
        String keyword = "1";

        QGuestBook qGuestBook = QGuestBook.guestBook;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qGuestBook.title.contains(keyword);
        builder.and(expression);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno"));

        Page<GuestBook> result = guestbookRepository.findAll(builder, pageable);
        result.stream().forEach(System.out::println);
    }

    @Test
    void testQuerydsl_제목or내용like검색_andgno는0보다크다() {
        String keyword = "1";

        QGuestBook qGuestBook = QGuestBook.guestBook;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestBook.title.contains(keyword);
        BooleanExpression exContent = qGuestBook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);
        builder.and(qGuestBook.gno.gt(0L));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        Page<GuestBook> result = guestbookRepository.findAll(builder, pageable);
        result.stream().forEach(System.out::println);
    }
}