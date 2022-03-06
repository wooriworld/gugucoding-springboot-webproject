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
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.guestbook.entity.GuestBook;
import org.zerock.guestbook.entity.QGuestBook;

import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
@SpringBootTest
class GuestbookRepositoryTest {
    // TODO 2022.03.06 WOORI MOCK객체 사용하도록 변경
    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    @Rollback(value = false)
    void insertBatch() {
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
    void insert() {
        //given
        GuestBook add = GuestBook.builder().title("title1").content("content1").writer("user1").build();

        //when
        guestbookRepository.save(add);

        //then
        GuestBook get = guestbookRepository.findById(add.getGno()).get();
        assertThat(add).isEqualTo(get);
    }

    @Test
    void update() {
        //given
        GuestBook add1 = GuestBook.builder().title("title1").content("content1").writer("user1").build();
        GuestBook add2 = GuestBook.builder().title("title2").content("content2").writer("user2").build();
        guestbookRepository.save(add1);
        guestbookRepository.save(add2);

        add1.changeTitle("Changed Title...");

        //when
        guestbookRepository.save(add1);

        //then
        GuestBook get1 = guestbookRepository.findById(add1.getGno()).get();
        GuestBook get2 = guestbookRepository.findById(add2.getGno()).get();

        assertThat(add1).isEqualTo(get1);
        assertThat(add2).isEqualTo(get2);
    }

    @Test
    void testQuerydsl_제목LIKE검색() {
        //given
        GuestBook searchTarget = GuestBook.builder().title("title1").content("content1").writer("user1").build();
        GuestBook notSearchTarget = GuestBook.builder().title("title2").content("content2").writer("user2").build();
        guestbookRepository.save(searchTarget);
        guestbookRepository.save(notSearchTarget);

        String keyword = "1";

        QGuestBook qGuestBook = QGuestBook.guestBook;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qGuestBook.title.contains(keyword);
        builder.and(expression);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno"));

        //when
        Page<GuestBook> page = guestbookRepository.findAll(builder, pageable);

        //then
        GuestBook searchResult = page.stream().findFirst().get();
        assertThat(searchTarget).isEqualTo(searchResult);
    }

    @Test
    void testQuerydsl_제목or내용like검색_and_gno는0보다크다() {
        //given
        GuestBook searchTarget = GuestBook.builder().title("title1").content("content1").writer("user1").build();
        GuestBook notSearchTarget = GuestBook.builder().title("title2").content("content2").writer("user2").build();
        guestbookRepository.save(searchTarget);
        guestbookRepository.save(notSearchTarget);

        String keyword = "1";

        QGuestBook qGuestBook = QGuestBook.guestBook;
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestBook.title.contains(keyword);
        BooleanExpression exContent = qGuestBook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);
        builder.and(exAll);
        builder.and(qGuestBook.gno.gt(0L));

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

        //when
        Page<GuestBook> page = guestbookRepository.findAll(builder, pageable);

        //then
        GuestBook searchResult = page.stream().findFirst().get();
        assertThat(searchTarget).isEqualTo(searchResult);
    }
}