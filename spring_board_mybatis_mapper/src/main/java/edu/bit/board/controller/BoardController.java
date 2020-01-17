package edu.bit.board.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.bit.board.page.Criteria;
import edu.bit.board.page.PageMaker;
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
		
		return "list";
	}
	
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		System.out.println("write_view()");
		
		return "write_view";
	}
	
	@RequestMapping("/write")
	public String write(BoardVO boardVO, Model model) {//Ŀ�ǵ尴ü�� �̿��� BoardVO��ü�� �ҷ���.
		System.out.println("write()");				//Ŀ�ǵ尴ü Ư���� �˾Ƽ� ���ͼ��� ����� ��.
		
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
	public String reply(BoardVO boardVO, Model model) {//Ŀ�ǵ尴ü�� �̿��� BoardVO��ü�� �ҷ���.
		System.out.println("reply()");				//Ŀ�ǵ尴ü Ư���� �˾Ƽ� ���ͼ��� ����� ��.
		
		boardService.writeReply(boardVO);//�亯 �ٴµ� ������ 2����. ������ 1�� ���������ִ°� �ϳ�,
		return "redirect:list";			//���� �� �亯�� ���� �μ�Ʈ �ϳ�.
	}
	
	@RequestMapping("/delete")
	public String delete(BoardVO boardVO, Model model) {//Ŀ�ǵ尴ü�� �̿��� BoardVO��ü�� �ҷ���.
		System.out.println("delete()");				//Ŀ�ǵ尴ü Ư���� �˾Ƽ� ���ͼ��� ����� ��.
		
		boardService.delete(boardVO);//�亯 �ٴµ� ������ 2����. ������ 1�� ���������ִ°� �ϳ�,
		return "redirect:list";			//���� �� �亯�� ���� �μ�Ʈ �ϳ�.
	}
	
	@RequestMapping("/list2")//����¡ó���ȸ���Ʈ
	public String list2(Criteria criteria, Model model) {
		System.out.println("list2()");
		
		PageMaker pageMaker = new PageMaker();//pageMaker ��ü �����ؼ�
		pageMaker.setCri(criteria);//�� �ȿ� criteria�� �־���.
		
		System.out.println(criteria.getPerPageNum());
		System.out.println(criteria.getPage());
		
		int totalCount = boardService.selectCountBoard();
		System.out.println("��ü �Խù� ��: "+totalCount);
		
		//��ü �� ���� �̰� �ϸ� pageMaker�� �ִ� ���� 11���� ���� ������.
		pageMaker.setTotalCount(totalCount);
		
		List<BoardVO> boardList = boardService.selectBoardListPage(criteria);
		//������ ����Ʈ�� �ִ°� �ٰ��������� ������ �� �������� ���°͸�ŭ �����;���
		//�׷����� ���� DB�� ������ִ°� �����;���. ó�� ������ 10���������ִ´�� 10�� �����;���
		//���� �ֱٺ��� 10��
		
		model.addAttribute("list",boardList);
		model.addAttribute("pageMaker",pageMaker);
		
		return "list2";
		
		//Criteria criteria �� ��� ������°�. 
	}

}
