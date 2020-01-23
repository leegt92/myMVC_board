package edu.bit.board.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.bit.board.page.Criteria;
import edu.bit.board.page.PageMaker;
import edu.bit.board.service.BoardService;
import edu.bit.board.vo.BoardVO;
import edu.bit.board.vo.UserVO;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Inject
	BoardService boardService;
	
	@RequestMapping("/list")
	public String list(Model model, HttpSession session) {
		System.out.println("list()");
		
		model.addAttribute("list",boardService.selectBoardList());
		
		/*
		 * //첫번째 방법. 그러니 이 방법을 쓰려면 모든 메소드에 이 방법을 집어넣어야 한다.
		 * //메소드 추가하면 이것도 다시 추가해야함. 이제인터셉터 써보겠음. 
		 * UserVO member = (UserVO)session.getAttribute("member");
		 *  if(member ==null) {
		 *   
		 *   System.out.println("멤버세션값 없음");
		 *   
		 *   }
		 *   
		 *	return "redirect:/";
		 */
		
		return "list";//servlet-context.xml 내부의 
		/*
		 * <beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		 * <beans:property name="prefix" value="/WEB-INF/views/" />
		 * <beans:property name="suffix" value=".jsp" />
		 * </beans:bean>
		 * 에 의해 list의 앞에는 WEB-INF/views/가 들어가고, 뒤에는 .jsp가 들어간다.
		 * 그 결과  WEB-INF/views/list.jsp 가 된다.
		 * url은 컨트롤러를 따라 board/board/list이다.
		 */
		
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
	
	@RequestMapping("/list2")//페이징처리된리스트
	public String list2(Criteria criteria, Model model) {
		System.out.println("list2()");
		
		PageMaker pageMaker = new PageMaker();//pageMaker 객체 생성해서
		pageMaker.setCri(criteria);//그 안에 criteria를 넣었음.
		
		System.out.println(criteria.getPerPageNum());
		System.out.println(criteria.getPage());
		
		int totalCount = boardService.selectCountBoard();
		System.out.println("전체 게시물 수: "+totalCount);
		
		//전체 값 세팅 이걸 하면 pageMaker에 있는 변수 11개에 전부 셋팅함.
		pageMaker.setTotalCount(totalCount);
		
		List<BoardVO> boardList = boardService.selectBoardListPage(criteria);
		//예전엔 리스트에 있는거 다가져왔지만 지금은 한 페이지에 들어가는것만큼 가져와야함
		//그럴려면 먼저 DB에 저장되있는걸 가져와야함. 처음 설정한 10개씩보여주는대로 10개 가져와야함
		//가장 최근부터 10개
		
		model.addAttribute("list",boardList);
		model.addAttribute("pageMaker",pageMaker);
		
		return "list2";
		
		//Criteria criteria 를 어디서 갖고오는가. 
	}

}
