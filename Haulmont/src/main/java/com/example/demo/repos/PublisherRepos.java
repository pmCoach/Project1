package com.example.demo.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entities.Book;
import com.example.demo.entities.Publisher;

public interface PublisherRepos extends JpaRepository<Publisher, Integer>{
	@Query("SELECT publisher FROM Publisher publisher WHERE " +
			"lower(publisher) LIKE lower(concat('%', :name, '%'))")
	public List<Publisher> findByName(@Param("name") String name);
}
