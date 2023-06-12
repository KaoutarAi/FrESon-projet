package fr.projet.api.utilisateur;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet.api.utilisateur.request.ConnexionRequest;
import fr.projet.api.utilisateur.request.InscriptionRequest;
import fr.projet.api.utilisateur.response.ConnexionResponse;
import fr.projet.api.utilisateur.response.UtilisateurResponse;
import fr.projet.config.jwt.JwtUtil;
import fr.projet.exception.utilisateur.InscriptionNotValidException;
import fr.projet.exception.utilisateur.UtilisateurNotFoundException;
import fr.projet.model.logging.Logging;
import fr.projet.model.utilisateur.Createur;
import fr.projet.model.utilisateur.Utilisateur;
import fr.projet.repo.ILoggingRepository;
import fr.projet.repo.IUtilisateurRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/utilisateur")
public class UtilisateurApiController {
	
	@Autowired
	private IUtilisateurRepository repoUtilisateur;
	
	@Autowired
	private ILoggingRepository repoLogging;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping
	public List<UtilisateurResponse> findAll() {
		return this.repoUtilisateur.findAll()
			.stream()
			.map(UtilisateurResponse::convert)
			.toList();
	}
	
	@GetMapping("/pseudo/{pseudo}")
	@Transactional
	public UtilisateurResponse findByPseudo(@PathVariable String pseudo) {
		Utilisateur utilisateur = this.repoUtilisateur.findByPseudo(pseudo).orElseThrow(UtilisateurNotFoundException::new);
		
		return UtilisateurResponse.convert(utilisateur);
	}
	
	@GetMapping("/{id}")
	@Transactional
	public UtilisateurResponse findById(@PathVariable int id) {
		Utilisateur utilisateur = this.repoUtilisateur.findById(id).orElseThrow(UtilisateurNotFoundException::new);
		
		return UtilisateurResponse.convert(utilisateur);
	}
	
	
	@PostMapping("/connexion")
	public ConnexionResponse connexion(@RequestBody ConnexionRequest connexionRequest) {
		
		Authentication authentication =
				new UsernamePasswordAuthenticationToken(connexionRequest.getPseudo(), connexionRequest.getMdp());

		this.authenticationManager.authenticate(authentication);

		ConnexionResponse response = new ConnexionResponse();

		String token = JwtUtil.generate(authentication);

        Utilisateur utilisateur = this.repoUtilisateur.findByPseudo(connexionRequest.getPseudo()).orElseThrow(UtilisateurNotFoundException::new);
		
		response.setSuccess(true);
		response.setToken(token); 
		response.setId(utilisateur.getId());
		response.setRole(utilisateur.getRole());
		
		return response;
	}
	
	
	@PostMapping("/inscription")
	public UtilisateurResponse inscription(@Valid @RequestBody InscriptionRequest inscriptionRequest, BindingResult result) {
		if (result.hasErrors()) {
			throw new InscriptionNotValidException();
		}
		if (inscriptionRequest.getMdp().equals(inscriptionRequest.getMdpVerif()) == false) {
            throw new InscriptionNotValidException();
        }
		
		Utilisateur utilisateur = new Utilisateur();
		Logging log = new Logging();
		
		if(inscriptionRequest.getRole().equals("CREATEUR")) {
			utilisateur = new Createur();
		}
		
		BeanUtils.copyProperties(inscriptionRequest, utilisateur);
		
		utilisateur.setMdp(this.passwordEncoder.encode(inscriptionRequest.getMdp()));
		
		log.setUtilisateur(utilisateur);
		log.setText("Inscription "+ utilisateur.getPseudo());
		
		this.repoUtilisateur.save(utilisateur);
		this.repoLogging.save(log);

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
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable int id) {
		this.repoUtilisateur.deleteById(id);
	}
	
	@DeleteMapping("/pseudo/{pseudo}")
	public void deleteByPseudo(@PathVariable String pseudo) {
		this.repoUtilisateur.deleteByPseudo(pseudo);
	}
}
