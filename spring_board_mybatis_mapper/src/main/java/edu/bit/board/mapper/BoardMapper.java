package edu.bit.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import edu.bit.board.vo.BoardVO;

//VO또는 DTO를 보고 만든다.

public interface BoardMapper {//이게 영속계층
							  //DAO임. 그리고 DAO에 들어갔던 함수들을 넣어야함.
								//원래 xml파일 만들어야 하지만, 간단하므로 마이바티스 사용 세번째방법으로 함
	@Select("select bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent from mvc_board order by bGroup desc, bStep asc")
	public List<BoardVO> selectBoardList();
	
	@Insert("insert into mvc_board (bId, bName, bTitle, bContent, bHit, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, #{bName}, #{bTitle}, #{bContent}, 0, mvc_board_seq.currval, 0, 0 )")
	public void insertBoard(BoardVO param);
	
	@Select("select * from mvc_board where bId = #{bId}")
	public BoardVO selectBoardOne(String bId);
	
	@Select("select count(*) from mvc_board")
	public int selectAllBoard();
	
	@Select("update mvc_board set bName = #{boardVO.bName}, bTitle = #{boardVO.bTitle}, bContent = #{boardVO.bContent} where bId = #{boardVO.bId}")
	public void updateBoard(@Param("boardVO")BoardVO boardVO);//하나만 있기때문에 위의 코드에서 boardVO를 지우거나 @Param을 적어야함.

	@Update("update mvc_board set bStep = bStep + 1 where bGroup = #{bGroup} and bStep > #{bStep}")
	public void updateShape(BoardVO boardVO);//객체 하나 받을땐 @Param 안적어도 됨.
	
	@Insert("insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent) values (mvc_board_seq.nextval, #{bName}, #{bTitle},#{bContent}, #{bGroup}, #{bStep}+1, #{bIndent}+1)")
	public void insertReply(BoardVO boardVO);

	@Delete("delete from mvc_board where bId = #{boardVO.bId}")
	public void delete(@Param("boardVO")BoardVO boardVO);
	
	
}
