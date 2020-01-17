package edu.bit.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.bit.board.mapper.BoardMapper;
import edu.bit.board.vo.BoardVO;

@Service
public class BoardService {
	
	//BoardMapper에 있는 메소드 그대로 가져와서 사용하는것.
	
	@Autowired
	BoardMapper boardMapper;
	
	//게시판 List를 위한 출력하는 로직											//서비스에 비즈니스로직이 들어감.
	public List<BoardVO> selectBoardList() {	//리스트는 값들을 뽑아오기 위한것으로
		return boardMapper.selectBoardList();	//코드는 한줄로 충분함.
	}
	
	//게시판에서 리플을 쓰는 로직에 sql 
	//step을 하나씩 밀어줘야함. update를 사용해 insert시 자신보다 step이 큰 모든 댓글에 step+1해줌.
	//그러면 아래 하나가 비는데 거기다 인서트.
	public void writeReply(BoardVO boardVO) {
		boardMapper.updateShape(boardVO);//댓글의 세로를 뜻하는 step을 지금 입력할 댓글 이외의 댓글 전부 찾아서 step+1
		boardMapper.insertReply(boardVO);//적은 리플을 위에 step+1을 한 덕에 빈 공간에 넣어야함.
	}
	//내가 쓴 글을 보드에 입력하는 로직
	public void insertBoard(BoardVO boardVO) {
		boardMapper.insertBoard(boardVO);
		// TODO Auto-generated method stub
		
	}
	//써져있는 글을 보는 로직, 글에 리플라이를 달 때 글쓰는창.
	public Object selectBoardOne(String bId) {
		
		return boardMapper.selectBoardOne(bId);
	}
	//수정하는 로직
	public void updateBoard(BoardVO boardVO) {
		boardMapper.updateBoard(boardVO);
		
	}

	public void delete(BoardVO boardVO) {
		boardMapper.delete(boardVO);
		
	}
}

