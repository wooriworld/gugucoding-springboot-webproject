package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    void register() {
        BoardDTO dto = BoardDTO.builder()
                        .title("test")
                        .content("test")
                        .writerEmail("user55@aaa.com")
                        .build();
        Long bno = boardService.register(dto);
    }

    @Test
    void getList() {
        PageRequestDTO requestDTO = new PageRequestDTO();
        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(requestDTO);
        for(BoardDTO boardDTO : result.getDtoList())
            System.out.println(boardDTO);
    }

    @Test
    void get() {
        BoardDTO boardDTO = boardService.get(100L);
        System.out.println(boardDTO);
    }

    @Test
    void delete() {
        boardService.removeWithReplies(1L);
    }


    @Test
    void modify() {
        BoardDTO boardDTO = BoardDTO.builder()
                        .bno(2L)
                        .title("수정")
                        .content("수정")
                        .build();
        boardService.modify(boardDTO);
    }
}