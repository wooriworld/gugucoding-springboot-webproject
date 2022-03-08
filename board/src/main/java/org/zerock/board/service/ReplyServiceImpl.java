package org.zerock.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;
import org.zerock.board.repository.ReplyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO dto) {
        replyRepository.save(entityToDto(dto));
        return dto.getRno();
    }

    @Override
    public List<ReplyDTO> getList(Long bno) {
        Board board = Board.builder().bno(bno).build();
        List<Reply> result = replyRepository.getRepliesByBoardOrderByRno(board);
        return result.stream().map(reply -> entityToDto(reply)).collect(Collectors.toList());
    }

    @Override
    public void modify(ReplyDTO dto) {
        replyRepository.save(entityToDto(dto));
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }
}
