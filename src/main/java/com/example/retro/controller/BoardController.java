package com.example.retro.controller;

import com.example.retro.exception.ResourceNotFoundException;
import com.example.retro.model.Board;
import com.example.retro.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardController {

	@Autowired
	BoardRepository boardRepository;

	@GetMapping("/boards")
	public List<Board> getAllBoards() {
		return boardRepository.findAll();
	}

	@PostMapping("/boards")
	public Board createBoard(@Valid @RequestBody Board board) {
		return boardRepository.save(board);
	}

	@GetMapping("/boards/{id}")
	public Board getBoardById(@PathVariable(value = "id") Long boardId) {
		return boardRepository.findById(boardId)
				.orElseThrow(() -> new ResourceNotFoundException("Board", "id", boardId));
	}

	@PutMapping("/boards/{id}")
	public Board updateBoard(@PathVariable(value = "id") Long boardId,
						   @Valid @RequestBody Board boardDetails) {

		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new ResourceNotFoundException("Board", "id", boardId));

		board.setName(boardDetails.getName());

		Board updatedBoard = boardRepository.save(board);
		return updatedBoard;
	}

	@DeleteMapping("/boards/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long boardId) {
		Board board = boardRepository.findById(boardId)
				.orElseThrow(() -> new ResourceNotFoundException("Board", "id", boardId));

		boardRepository.delete(board);

		return ResponseEntity.ok().build();
	}


}