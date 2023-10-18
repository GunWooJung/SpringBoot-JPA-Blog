package com.cos.blog.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.model.Board;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	@Transactional
	public void enroll(Board board, User user) {
			board.setUser(user);
			boardRepository.save(board);
	}
	@Transactional(readOnly=true)
	public Page<Board> list(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	@Transactional(readOnly=true)
	public Board showDetail(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글을 상세 볼 수 없습니다.");
				});
	}
	@Transactional
	public void deleteById(int id) {
		boardRepository.deleteById(id);
	}

}
