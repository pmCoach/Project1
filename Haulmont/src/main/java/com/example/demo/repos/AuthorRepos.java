package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Author;

public interface AuthorRepos extends JpaRepository<Author, Integer>{
	@Query("SELECT author FROM Author author WHERE " +
			"lower(author.name) LIKE lower(concat('%', :name, '%'))")
public List<Author> findByName(@Param("name") String name);
}
