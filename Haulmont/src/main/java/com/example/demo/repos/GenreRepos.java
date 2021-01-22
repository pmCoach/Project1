package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Genre;

public interface GenreRepos extends JpaRepository<Genre, Integer>{
	
	@Query("SELECT genre FROM Genre genre WHERE lower(genre.name) LIKE lower(concat('%', :name, '%'))")
	public List<Genre> findByName(@Param("name") String name);
}
