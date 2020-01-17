package edu.bit.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.bit.board.mapper.BoardMapper;
import edu.bit.board.vo.BoardVO;

@Service
public class BoardService {
	
	//BoardMapper�� �ִ� �޼ҵ� �״�� �����ͼ� ����ϴ°�.
	
	@Autowired
	BoardMapper boardMapper;
	
	//�Խ��� List�� ���� ����ϴ� ����											//���񽺿� ����Ͻ������� ��.
	public List<BoardVO> selectBoardList() {	//����Ʈ�� ������ �̾ƿ��� ���Ѱ�����
		return boardMapper.selectBoardList();	//�ڵ�� ���ٷ� �����.
	}
	
	//�Խ��ǿ��� ������ ���� ������ sql 
	//step�� �ϳ��� �о������. update�� ����� insert�� �ڽź��� step�� ū ��� ��ۿ� step+1����.
	//�׷��� �Ʒ� �ϳ��� ��µ� �ű�� �μ�Ʈ.
	public void writeReply(BoardVO boardVO) {
		boardMapper.updateShape(boardVO);//����� ���θ� ���ϴ� step�� ���� �Է��� ��� �̿��� ��� ���� ã�Ƽ� step+1
		boardMapper.insertReply(boardVO);//���� ������ ���� step+1�� �� ���� �� ������ �־����.
	}
	//���� �� ���� ���忡 �Է��ϴ� ����
	public void insertBoard(BoardVO boardVO) {
		boardMapper.insertBoard(boardVO);
		// TODO Auto-generated method stub
		
	}
	//�����ִ� ���� ���� ����, �ۿ� ���ö��̸� �� �� �۾���â.
	public Object selectBoardOne(String bId) {
		
		return boardMapper.selectBoardOne(bId);
	}
	//�����ϴ� ����
	public void updateBoard(BoardVO boardVO) {
		boardMapper.updateBoard(boardVO);
		
	}

	public void delete(BoardVO boardVO) {
		boardMapper.delete(boardVO);
		
	}
}

