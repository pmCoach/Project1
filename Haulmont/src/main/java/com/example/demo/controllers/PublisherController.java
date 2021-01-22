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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Publisher;
import com.example.demo.repos.PublisherRepos;

@Controller
public class PublisherController {
@Autowired
private PublisherRepos publisherRepos;

@GetMapping("/publishers")
public String getPublishers(Model model) {
	List<Publisher> publishers = publisherRepos.findAll();
	model.addAttribute("publishers", publishers);
	return "publisher";
}

@GetMapping("/publisher_add")
public String publisherAdd() {
	return "publisherAdd";
}

@PostMapping("/publisher_add")
public String publisherAdd(@RequestParam String name) {
	Publisher publisher = new Publisher(name);
	publisherRepos.save(publisher);
	return "redirect:/publishers";
}

@GetMapping("publishers/{id}/edit")
public String publisherEdit(@PathVariable Integer id, Model model){
	Optional<Publisher> publisher = publisherRepos.findById(id);
	ArrayList<Publisher> res = new ArrayList<Publisher>();
	publisher.ifPresent(res :: add);
	model.addAttribute("publisher", res);
	return "updatePublisher";
}

@PostMapping("publishers/{id}/edit")
public String publisherEdit(@PathVariable Integer id, @RequestParam String name) {
	Publisher publisher = publisherRepos.findById(id).orElseThrow();
	publisher.setName(name);
	publisherRepos.save(publisher);
	return "redirect:/publishers";
}

@GetMapping("publisher/{id}/delete")
public String publisherDelete(@PathVariable Integer id) {
	publisherRepos.deleteById(id);
	return "redirect:/publishers";
}

@PostMapping("publisher/search")
public String publisherSearch(@RequestParam String name, Model model) {
	List<Publisher> publishers = publisherRepos.findByName(name);
	model.addAttribute("publishers", publishers);
	return "publisher";
}
}
