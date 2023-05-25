package fr.projet.api.logging;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet.api.logging.request.LoggingRequest;
import fr.projet.api.logging.response.LoggingResponse;
import fr.projet.exception.logging.LogNotFoundException;
import fr.projet.exception.logging.LogNotValidException;
import fr.projet.model.logging.Logging;
import fr.projet.repo.ILoggingRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/logging")
@CrossOrigin("*")
public class LoggingApiController {
	@Autowired
	private ILoggingRepository repoLogging;
	
	@GetMapping
	@Transactional
	public List<LoggingResponse> findAll(){
		return this.repoLogging.findAll()
				.stream()
				.map(LoggingResponse::convert)
				.toList();
	}
	
	@PostMapping
	public Logging add(@Valid @RequestBody LoggingRequest loggingRequest, BindingResult result) {
		if (result.hasErrors()) {
			throw new LogNotValidException();
		}
		Logging logging = new Logging();
		
		BeanUtils.copyProperties(loggingRequest, logging);
		System.out.println(logging.getText());
		System.out.println(logging.getId());
		System.out.println(logging.getDate());
		System.out.println(logging.getUtilisateur().getNom());
		
		
		logging.setDate(LocalDateTime.now());
		
		return this.repoLogging.save(logging);
		
	}
	
	@PutMapping("/{id}")
	public Logging edit(@PathVariable int id, @Valid @RequestBody LoggingRequest loggingRequest, BindingResult result) {
		if (result.hasErrors()) {
			throw new LogNotValidException();
		}
		Logging logging = this.repoLogging.findById(id).orElseThrow(LogNotFoundException::new);
		
		BeanUtils.copyProperties(loggingRequest, logging);
		
		return this.repoLogging.save(logging);
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable int id) {
		if (this.repoLogging.findById(id).isEmpty()) {
			throw new LogNotFoundException();
		}
		this.repoLogging.deleteById(id);
	}
}
