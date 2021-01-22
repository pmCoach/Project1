package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Book;

public interface BookRepos extends JpaRepository<Book, Integer>{
	
	@Query(value="SELECT book FROM Book book WHERE lower(book.name) LIKE lower(concat('%', :name, '%'))")
	public List<Book> findByName(@Param("name") String name);
}