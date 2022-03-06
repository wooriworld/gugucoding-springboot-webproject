package org.zerock.guestbook.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.GuestBook;
import org.zerock.guestbook.repository.GuestbookRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class GuestbookServiceImplTest {

    @Autowired
    private GuestbookService service;

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    void register() {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("title")
                .content("content")
                .writer("user")
                .build();
        service.register(guestbookDTO);
    }

    @Test
    void getList() {
        GuestBook add1 = GuestBook.builder().title("title1").content("content1").writer("user1").build();
        GuestBook add2 = GuestBook.builder().title("title2").content("content2").writer("user2").build();
        guestbookRepository.save(add1);
        guestbookRepository.save(add2);

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<GuestbookDTO, GuestBook> pageResultDTO = service.getList(pageRequestDTO);

        assertThat(pageResultDTO.getDtoList().size()).isEqualTo(2);
        assertThat(service.entityToDto(add1)).isEqualTo(pageResultDTO.getDtoList().get(1));
        assertThat(service.entityToDto(add2)).isEqualTo(pageResultDTO.getDtoList().get(0));

        assertThat(pageResultDTO.isPrev()).isFalse();
        assertThat(pageResultDTO.isNext()).isFalse();
        assertThat(pageResultDTO.getTotalPage()).isEqualTo(0);
        assertThat(pageResultDTO.getPageList()).isNull();
    }
}