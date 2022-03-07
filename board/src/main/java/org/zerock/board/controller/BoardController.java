package org.zerock.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.service.BoardService;

@Controller
@RequestMapping("/board")
@Log4j2
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @GetMapping("/list")
    public void list(Model model, PageRequestDTO pageRequestDTO) {
        log.info("list............");
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/register")
    public void register() {
        log.info("register............");
    }

    @PostMapping("/register")
    public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes) {
        Long bno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg", bno);
        return "redirect:/board/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(long bno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("read............");
        BoardDTO dto = service.get(bno);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String modify(BoardDTO dto, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, RedirectAttributes redirectAttributes) {
        service.modify(dto);
        redirectAttributes.addFlashAttribute("page", requestDTO.getPage());
        redirectAttributes.addFlashAttribute("type", requestDTO.getType());
        redirectAttributes.addFlashAttribute("keyword", requestDTO.getKeyword());
        redirectAttributes.addFlashAttribute("bno", dto.getBno());
        return "redirect:/board/read";
    }

    @PostMapping("/remove")
    public String remove(long bno, RedirectAttributes redirectAttributes) {
        log.info("gno: " + bno);

        service.removeWithReplies(bno);

        redirectAttributes.addFlashAttribute("msg", bno);

        return "redirect:/board/list";
    }
}
