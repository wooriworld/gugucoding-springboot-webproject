package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.ReplyDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReplyServiceTest {
    @Autowired
    private ReplyService service;

    @Test
    void testGetList() {
        List<ReplyDTO> list = service.getList(100L);
        list.forEach(System.out::println);
    }
}