package com.dhttp.storage.controller;

import com.dhttp.data.RequestType;
import com.dhttp.storage.exception.IdNotFoundException;
import com.dhttp.storage.model.HttpEntity;
import com.dhttp.storage.repository.HttpEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HttpEntityController {
	private final HttpEntityRepository repository;

	@Autowired
	public HttpEntityController(HttpEntityRepository repository) {
		this.repository = repository;
	}

	@GetMapping("{id}")
	public HttpEntity getById(@PathVariable String id) throws IdNotFoundException {
		return repository
				.findById(id)
				.orElseThrow(() -> IdNotFoundException.httpEntityIdNotFound(id));
	}

	@GetMapping("{id}/source")
	@ResponseBody
	public String getSourceById(@PathVariable String id) throws IdNotFoundException {
		return repository
				.findById(id)
				.orElseThrow(() -> IdNotFoundException.httpEntityIdNotFound(id))
				.getSource();
	}

	@PostMapping
	public HttpEntity createNew(@RequestParam String url, @RequestParam RequestType type, @RequestBody String httpSource) {
		return repository.save(new HttpEntity().setUrl(url).setType(type.toString()).setSource(httpSource));
	}
}
