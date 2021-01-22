package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Author;
import com.example.demo.entities.Genre;
import com.example.demo.repos.AuthorRepos;
import com.example.demo.repos.GenreRepos;

@Controller
public class GenreController {
@Autowired
private GenreRepos genreRepos;

	@GetMapping("/genres")
	public String genresShow(Model model) {
		List<Genre> genres = genreRepos.findAll();
		model.addAttribute("genres", genres);
		return "genres";
	}
	
	@GetMapping("/genre/{id}/edit")
	public String genreEdit(@PathVariable Integer id, Model model) {
		if (!genreRepos.existsById(id))
			return "redirect:/genres";
		Optional<Genre> genre = genreRepos.findById(id);
		ArrayList<Genre> res = new ArrayList<Genre>();
		genre.ifPresent(res::add);
		model.addAttribute("genre", res);
		return "updateGenre";
	}
	
	@GetMapping("/genre/{id}/delete")
	public String genreDelete(@PathVariable Integer id) {
		genreRepos.deleteById(id);
		return "redirect:/genres";
	}
	
	@PostMapping("/genre/{id}/edit")
	public String genreEdit(@PathVariable Integer id, @RequestParam String name) {
		Genre genre = genreRepos.findById(id).orElseThrow();
		genre.setName(name);
		genreRepos.save(genre);
		return "redirect:/genres";
	}
	
	@GetMapping("genre_add")
	public String genre_add() {
		return "genre_add";
	}
	
	@PostMapping("genre_add")
	public String genre_add(@RequestParam String name, 
			Model model) {
		Genre genre = new Genre(name);
		genreRepos.save(genre);
		return "redirect:/genres";
	}
	
	@PostMapping("/genres/search")
	public String genresSearch(@RequestParam String name, Model model) {
		List<Genre> genres = genreRepos.findByName(name);
		model.addAttribute("genres", genres);
		return "genres";
	}
}
