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
import com.example.demo.entities.Genre;
import com.example.demo.entities.Publisher;
import com.example.demo.repos.AuthorRepos;
import com.example.demo.repos.BookRepos;
import com.example.demo.repos.GenreRepos;
import com.example.demo.repos.PublisherRepos;

@Controller
public class BooksControllers {
	@Autowired
	private BookRepos bookRepos;
	@Autowired
	private AuthorRepos authorRepos;
	@Autowired
	private GenreRepos genreRepos;
	@Autowired
	private PublisherRepos publisherRepos;
	
	@GetMapping("/books")
	public String books(Model model) {
		List<Book> books = bookRepos.findAll();
		model.addAttribute("books", books);
		return "books";
	}
	
	@GetMapping ("/book_add")
	public String bookAdd(Model model) {
		List<Author> authors = authorRepos.findAll();
		List<Genre> genres = genreRepos.findAll();
		List<Publisher> publishers = publisherRepos.findAll();
		
		model.addAttribute("authors", authors);
		model.addAttribute("genres", genres);
		model.addAttribute("publishers", publishers);
		return "BooksAdd";
	}
	
	@PostMapping("/book_add")
	public String bookAdd(
			@RequestParam String name,
			@RequestParam String publisher,
			@RequestParam String author,
			@RequestParam String genre,
			@RequestParam String year,
			@RequestParam String city) {
		Book book = new Book(name, author, genre, publisher, year, city);
		bookRepos.save(book);
		return "redirect:/books";
	}
	
	@GetMapping("book/{id}/edit")
	public String bookEdit(@PathVariable Integer id, Model model) {
		Optional<Book> book = bookRepos.findById(id);
		List<Author> authors = authorRepos.findAll();
		List<Genre> genres = genreRepos.findAll();
		List<Publisher> publishers = publisherRepos.findAll();
		ArrayList<Book> res = new ArrayList<Book>();
		book.ifPresent(res :: add);
		model.addAttribute("book", res);
		model.addAttribute("genres", genres);
		model.addAttribute("publishers", publishers);
		model.addAttribute("authors", authors);
		return "booksEdit";
	}
	
	@PostMapping("book/{id}/edit")
	public String bookEdit(@PathVariable Integer id,
			@RequestParam String name,
			@RequestParam String publisher,
			@RequestParam String author,
			@RequestParam String genre,
			@RequestParam String year,
			@RequestParam String city
			) {
		Book book = bookRepos.findById(id).orElseThrow();
		book.setName(name);
		book.setPublisher(publisher);
		book.setAuthor(author);
		book.setGenre(genre);
		book.setYear(year);
		book.setCity(city);
		bookRepos.save(book);
		return "redirect:/books";
	}
	
	@GetMapping("book/{id}/delete")
	public String bookDelete(@PathVariable Integer id) {
		bookRepos.deleteById(id);
		return "redirect:/books";
	}
	
	@PostMapping("/books/search")
	public String bookSearch(@RequestParam String name, Model model) {
		List<Book> books = bookRepos.findByName(name);
		model.addAttribute("books", books);
		return "books";
	}
}
