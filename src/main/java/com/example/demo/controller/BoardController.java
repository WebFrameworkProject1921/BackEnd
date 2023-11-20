package com.example.demo.controller;

import com.example.demo.domain.Board;
import com.example.demo.domain.Card;
import com.example.demo.domain.Column;
import com.example.demo.domain.Place;
import com.example.demo.file.FileStore;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller //HTTP 요청 처리, 웹페이지 렌더링
@CrossOrigin(origins = "*")
@RestController //JSON 또는 XML 데이터 반환
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardRepository boardRepository;
    private final FileStore fileStore;

    @GetMapping
    public List<Board> Boards(@RequestParam Long authorId) { //해당 유저의 보드 목록을 가져옴
        List<Board> boards = boardRepository.findAll(authorId);
        //log.info("boards={}", boards);
        return boards;
    }

    @PostMapping
    public String addBoard(@RequestBody BoardForm boardForm) throws IOException { //새 보드 추가

        List<Column> columns = new ArrayList<>();
        //데이터베이스에 저장

        Board board = new Board();

        board.setId(boardForm.getId());
        board.setBoardTitle(boardForm.getBoardTitle());
        board.setAuthorId(boardForm.getAuthorId());
        board.setDuration(boardForm.getDuration());
        board.setColumnList(columns);

        boardRepository.save(board);

        return "Success";
    }
    @PutMapping("/{boardId}") //보드수정
    public String updateBoard(@PathVariable String boardId, @RequestBody BoardForm boardForm) throws IOException {
        Board board = boardRepository.findById(boardId); //기존 보드 가져오기
        log.info("boards-Update={}", board);
        log.info("boardsForm-Update={}", boardForm);
        List<Column> columnForms = boardForm.getColumnList();

        List<Column> columns = new ArrayList<>();
        if(columnForms != null) {
            for (Column columnForm : columnForms) {
                Column column = new Column();

                // CardForm 리스트를 Card 리스트로 변환
                List<Card> cardForms = columnForm.getCards();
                List<Card> cards = new ArrayList<>();
                if (cardForms != null) {
                    for (Card cardForm : cardForms) {
                        Card card = new Card();
                        card.setId(cardForm.getId());
                        card.setMemo(cardForm.getMemo());
                        Place place = new Place();
                        place.setName(cardForm.getPlace().getName());
                        place.setAddress(cardForm.getPlace().getAddress());
                        place.setPhone(cardForm.getPlace().getPhone());
                        place.setCategory(cardForm.getPlace().getCategory());
                        place.setCategoryDetail(cardForm.getPlace().getCategoryDetail());
                        place.setLat(cardForm.getPlace().getLat());
                        place.setLng(cardForm.getPlace().getLng());

                        card.setPlace(place);
                        cards.add(card);
                    }
                }
                column.setCards(cards);
                columns.add(column);
            }
        }

        board.setBoardTitle(boardForm.getBoardTitle());
        board.setDuration(boardForm.getDuration());
        board.setAuthorId(board.getAuthorId());
        board.setColumnList(columns);
        log.info("boards-Update2={}", board);
        boardRepository.save(board);
        return "Success";
    }

    @DeleteMapping("/{boardId}") //보드삭제
    public String deleteBoard(@PathVariable String boardId) {
        Board boardToDelete = boardRepository.findById(boardId);
        if (boardToDelete != null) {
            boardRepository.delete(boardId);
            return "Success";
        } else {
            return "Board not found";
        }

    }
}
