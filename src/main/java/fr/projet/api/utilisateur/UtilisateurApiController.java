package fr.projet.api.utilisateur;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet.api.utilisateur.request.InscriptionRequest;
import fr.projet.api.utilisateur.response.UtilisateurResponse;
import fr.projet.exception.utilisateur.InscriptionNotValidException;
import fr.projet.exception.utilisateur.UtilisateurNotFoundException;
import fr.projet.model.utilisateur.Utilisateur;
import fr.projet.repo.IUtilisateurRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurApiController {
	
	@Autowired
	private IUtilisateurRepository repoUtilisateur;
	
	@GetMapping
	public List<UtilisateurResponse> findAll() {
		return this.repoUtilisateur.findAll()
			.stream()
			.map(UtilisateurResponse::convert)
			.toList();
	}
	
//	@GetMapping
//	public List<UtilisateurResponse> findAll() {
//		List<UtilisateurResponse> response = new ArrayList<>();
//		List<Utilisateur> utilisateurs = this.repoUtilisateur.findAll();
//		
//		for (Utilisateur utilisateur : utilisateurs) {
//		
//			response.add(UtilisateurResponse.convert(utilisateur));
//		}
//		
//		return response;
//	}
	
	@GetMapping("/{pseudo}")
	@Transactional
	public UtilisateurResponse findByPseudo(@PathVariable String pseudo) {
		Utilisateur utilisateur = this.repoUtilisateur.findByPseudo(pseudo).orElseThrow(UtilisateurNotFoundException::new);
		
		return UtilisateurResponse.convert(utilisateur);
	}
	
//	@GetMapping("/{id}")
//	@Transactional
//	public UtilisateurResponse findById(@PathVariable int id) {
//		Utilisateur utilisateur = this.repoUtilisateur.findById(id).orElseThrow(UtilisateurNotFoundException::new);
//		
//		return UtilisateurResponse.convert(utilisateur);
//	}
	
	@PostMapping
	public UtilisateurResponse inscription(@Valid @RequestBody InscriptionRequest inscriptionRequest, BindingResult result) {
		if (result.hasErrors()) {
			throw new InscriptionNotValidException();
		}
		
		if (inscriptionRequest.getMdp().equals(inscriptionRequest.getMdpVerif()) == false) {
			throw new InscriptionNotValidException();
		}
		
		Utilisateur utilisateur = new Utilisateur();
		
		BeanUtils.copyProperties(inscriptionRequest, utilisateur);
		
		this.repoUtilisateur.save(utilisateur);
		
		return UtilisateurResponse.convert(utilisateur);
	}
	
	@PutMapping("/{id}")
	public Utilisateur edit(@PathVariable int id, @Valid @RequestBody InscriptionRequest inscriptionRequest, BindingResult result) {
		if (result.hasErrors()) {
			throw new InscriptionNotValidException();
		}
		
		Utilisateur utilisateur = this.repoUtilisateur.findById(id).orElseThrow(UtilisateurNotFoundException::new);
		
		BeanUtils.copyProperties(inscriptionRequest, utilisateur);
		
		return this.repoUtilisateur.save(utilisateur);
	}
	
//	@DeleteMapping("/{id}")
//	public void deleteById(@PathVariable int id) {
//		this.repoUtilisateur.deleteById(id);
//	}
	
	@DeleteMapping("/{pseudo}")
	public void deleteByPseudo(@PathVariable String pseudo) {
		this.repoUtilisateur.deleteByPseudo(pseudo);
	}
}
