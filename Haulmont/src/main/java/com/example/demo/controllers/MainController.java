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
import com.example.demo.entities.Book;
import com.example.demo.repos.AuthorRepos;
import com.example.demo.repos.BookRepos;

@Controller
public class MainController {
	@Autowired
	private AuthorRepos authorRepos;
	@Autowired
	private BookRepos bookRepos;
	
	@GetMapping("/")
	public String main() {
		return "main";
	}
	
	
	@GetMapping("authors")
	public String showAuthors(Model model) {
		List<Author> authors = authorRepos.findAll();
		model.addAttribute("authors", authors);
		return "autors";
	}
	
	@GetMapping("author_add")
	public String author_add() {
		return "autor_add";
	}
	
	@PostMapping("author_add")
	public String author_add(@RequestParam String name, 
			@RequestParam String secname,
			@RequestParam String patronymic,
			Model model) {
		Author author = new Author(name, secname, patronymic);
		authorRepos.save(author);
		List<Author> authors = authorRepos.findAll();
		model.addAttribute("authors", authors);
		return "redirect:/authors";
	}
	
	@GetMapping("update/{id}")
	public String  updateAuthor(@PathVariable Integer id, Model model) {
		if (!authorRepos.existsById(id))
			return "redirect:/authors";
		Optional<Author> author = authorRepos.findById(id);
		ArrayList<Author> res = new ArrayList<>();
		author.ifPresent(res::add);
		model.addAttribute("author", res);
		return "updateAuthor";
	}
	
	@PostMapping("update/{id}")
	public String updateAuthor(@PathVariable Integer id,
			@RequestParam String name,
			@RequestParam String secname,
			@RequestParam String patronymic
			) {
		Author author = authorRepos.findById(id).orElseThrow();
		author.setName(name);
		author.setSecname(secname);
		author.setPatronymic(patronymic);
		authorRepos.save(author);
		return "redirect:/authors";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteAuthor(@PathVariable Integer id) {
		authorRepos.deleteById(id);
		return "redirect:/authors";
	}
	
	@PostMapping("/autors/search")
	public String autorSearch(@RequestParam String name, Model model) {
		List<Author> autors = authorRepos.findByName(name);
		model.addAttribute("authors", autors);
		return "autors";
		
	}
}
