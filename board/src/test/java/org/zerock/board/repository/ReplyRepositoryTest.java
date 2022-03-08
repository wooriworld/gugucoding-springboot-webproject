package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    void insertReply() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            long bno = (long)(Math.random()*100)+1;
            Board board = Board.builder().bno(bno).build();
            Reply reply = Reply.builder()
                    .text("Text"+i)
                    .replyer("guest")
                    .board(board)
                    .build();
            replyRepository.save(reply);
        });
    }

    @Test
    void testRead1() {
        Optional<Reply> result = replyRepository.findById(1L);

        if(result.isPresent()) {
            Reply reply = result.get();

            System.out.println(reply);
            System.out.println(reply.getBoard());
        }
    }

    @Test
    void testListByBoard() {
        List<Reply> replyList = replyRepository.getRepliesByBoardOrderByRno(Board.builder().bno(97L).build());
        replyList.forEach(System.out::println);
    }
}