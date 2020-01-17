package edu.bit.board.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.bit.board.service.BoardService;
import edu.bit.board.vo.BoardVO;

@Controller
public class BoardController {
	
	@Inject
	BoardService boardService;
	
	@RequestMapping("/list")
	public String list(Model model) {
		System.out.println("list()");
		
		model.addAttribute("list",boardService.selectBoardList());
		System.out.println("");
		return "list";
	}
	
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		System.out.println("write_view()");
		
		return "write_view";
	}
	
	@RequestMapping("/write")
	public String write(BoardVO boardVO, Model model) {//커맨드객체를 이용해 BoardVO객체를 불러옴.
		System.out.println("write()");				//커맨드객체 특성상 알아서 겟터셋터 사용할 것.
		
		boardService.insertBoard(boardVO);
		return "redirect:list";
	}
	
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		System.out.println("content_view()");				
		
		String bId = request.getParameter("bId");
		model.addAttribute("content_view",boardService.selectBoardOne(bId));
		return "content_view";
	}
	
	@RequestMapping(value = "/modify",method = RequestMethod.POST)
	public String modify(BoardVO boardVO, Model model) {
		System.out.println("modify");		
		
		boardService.updateBoard(boardVO);
		return "redirect:list";
	}
	
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model) {
		System.out.println("reply_view()");				
		
		String bId = request.getParameter("bId");
		model.addAttribute("reply_view",boardService.selectBoardOne(bId));
		return "reply_view";
	}
	
	@RequestMapping("/reply")
	public String reply(BoardVO boardVO, Model model) {//커맨드객체를 이용해 BoardVO객체를 불러옴.
		System.out.println("reply()");				//커맨드객체 특성상 알아서 겟터셋터 사용할 것.
		
		boardService.writeReply(boardVO);//답변 다는데 쿼리가 2개들어감. 스텝을 1씩 증가시켜주는것 하나,
		return "redirect:list";			//내가 쓴 답변이 들어가는 인서트 하나.
	}
	
	@RequestMapping("/delete")
	public String delete(BoardVO boardVO, Model model) {//커맨드객체를 이용해 BoardVO객체를 불러옴.
		System.out.println("delete()");				//커맨드객체 특성상 알아서 겟터셋터 사용할 것.
		
		boardService.delete(boardVO);//답변 다는데 쿼리가 2개들어감. 스텝을 1씩 증가시켜주는것 하나,
		return "redirect:list";			//내가 쓴 답변이 들어가는 인서트 하나.
	}

}
