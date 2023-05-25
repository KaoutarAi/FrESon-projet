package fr.projet.api.logging;

import java.time.LocalDateTime;
import java.util.List;

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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/logging")
@CrossOrigin("*")
public class LoggingApiController {
	@Autowired
	private ILoggingRepository repoLogging;
	
	@GetMapping
	public List<LoggingResponse> findAll(){
		return this.repoLogging.findAll()
				.stream()
				.map(LoggingResponse::convert)
				.toList();
	}
	
	@GetMapping("/{year}-{month}-{day}")
	public List<LoggingResponse> findByDay(@PathVariable int year,@PathVariable int month,@PathVariable int day ){
		return this.repoLogging.findByDate(year, month, day)
				.stream()
				.map(LoggingResponse::convert)
				.toList();
	}
	
	@GetMapping("/{year}/{month}")
	public List<LoggingResponse> findByMonth(@PathVariable int year,@PathVariable int month){
		return this.repoLogging.findByMonth(year, month)
				.stream()
				.map(LoggingResponse::convert)
				.toList();
	}
	
	@GetMapping("/{year}")
	public List<LoggingResponse> findByYear(@PathVariable int year){
		return this.repoLogging.findByYear(year)
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
	
	@DeleteMapping("/{year}/{month}/{day}")
	public void deleteByDay(@PathVariable int year, @PathVariable int month, @PathVariable int day){
		List<Logging> toDelete = this.repoLogging.findByDate(year, month, day);
		for (Logging l : toDelete) {
			System.out.println("Suppression du Log "+l.getId());
			this.repoLogging.deleteById(l.getId());
		}
	}
	
	@DeleteMapping("/{year}/{month}")
	public void deleteByMonth(@PathVariable int year, @PathVariable int month){
		List<Logging> toDelete = this.repoLogging.findByMonth(year, month);
		for (Logging l : toDelete) {
			System.out.println("Suppression du Log "+l.getId());
			this.repoLogging.deleteById(l.getId());
		}
	}
	
	@DeleteMapping("/{year}")
	public void deleteByDate(@PathVariable int year){
		List<Logging> toDelete = this.repoLogging.findByYear(year);		
		for (Logging l : toDelete) {
			System.out.println("Suppression du Log "+l.getId());
			this.repoLogging.deleteById(l.getId());
		}
	}
}
