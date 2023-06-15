package fr.projet.api.utilisateur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet.api.musique.response.MusiqueResponse;
import fr.projet.api.musique.response.PlaylistResponse;
import fr.projet.api.utilisateur.request.ConnexionRequest;
import fr.projet.api.utilisateur.request.InscriptionRequest;
import fr.projet.api.utilisateur.request.ResetMdpRequest;
import fr.projet.api.utilisateur.request.UtilisateurRequest;
import fr.projet.api.utilisateur.response.ConnexionResponse;
import fr.projet.api.utilisateur.response.UtilisateurResponse;
import fr.projet.config.jwt.JwtUtil;
import fr.projet.exception.PlaylistNotFoundException;
import fr.projet.exception.utilisateur.EntityNotValidException;
import fr.projet.exception.utilisateur.InscriptionNotValidException;
import fr.projet.exception.utilisateur.UtilisateurNotFoundException;
import fr.projet.model.logging.Logging;
import fr.projet.model.musique.Playlist;
import fr.projet.model.utilisateur.Createur;
import fr.projet.model.utilisateur.Utilisateur;
import fr.projet.repo.ILoggingRepository;
import fr.projet.repo.IPlaylistRepository;
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
	private IPlaylistRepository repoPlaylist;

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

	@GetMapping("/roles")
	public List<UtilisateurResponse> findAllbyRoles() {
		return this.repoUtilisateur.findAllByRoles()
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

	@GetMapping("/favoris/playlists")
	@Transactional
	public List<PlaylistResponse> findAboPlaylist(@RequestHeader("Authorization") String token) {

		String pseudo = UtilisateurConnecte.getPseudo(token);
		Utilisateur utilisateur = this.repoUtilisateur.findByPseudo(pseudo).orElseThrow(UtilisateurNotFoundException::new);
		return utilisateur.getAbonnements().stream()
                .map(PlaylistResponse::new)
                .toList();
	}

	@PutMapping("/abo-playlist/{playlistId}")
	@Transactional
	public List<PlaylistResponse> likePlaylist(@RequestHeader("Authorization") String token, @PathVariable int playlistId){

		String pseudo = UtilisateurConnecte.getPseudo(token);
		Utilisateur utilisateur = this.repoUtilisateur.findByPseudo(pseudo).orElseThrow(UtilisateurNotFoundException::new);
		Playlist playlist = this.repoPlaylist.findById(playlistId).orElseThrow(PlaylistNotFoundException::new);

		if(!utilisateur.getAbonnements().contains(playlist)) {
			utilisateur.getAbonnements().add(playlist);
		}
		else {
			utilisateur.getAbonnements().remove(playlist);
		}

        this.repoUtilisateur.save(utilisateur);

		return utilisateur.getAbonnements().stream()
                .map(PlaylistResponse::new)
                .toList();
	}


	@GetMapping("/favoris/musiques")
	@Transactional
	public List<MusiqueResponse> findAboMusique(@RequestHeader("Authorization") String token){
		List<MusiqueResponse> response = new ArrayList<>();
		for(PlaylistResponse pl: this.findAboPlaylist(token)) {
			response.addAll(pl.getMusiques());
		}
		Collections.shuffle(response);

		return response;
	}

	@GetMapping("/mes-playlists")
	@Transactional
	public List<PlaylistResponse> mesPlaylists(@RequestHeader("Authorization") String token){
		String pseudo = UtilisateurConnecte.getPseudo(token);
		Utilisateur utilisateur = this.repoUtilisateur.findByPseudo(pseudo).orElseThrow(UtilisateurNotFoundException::new);
		return utilisateur.getPlaylists().stream()
                .map(PlaylistResponse::new)
                .toList();
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

	
	@PutMapping("/params")
	public ResponseEntity<Utilisateur> edit(@RequestHeader("Authorization") String token, @Valid @RequestBody UtilisateurRequest userRequest, BindingResult result) {

		if (result.hasErrors()) {
			throw new EntityNotValidException();
		}
		
		String pseudo = UtilisateurConnecte.getPseudo(token);
		Utilisateur utilisateur = this.repoUtilisateur.findByPseudo(pseudo).orElseThrow(UtilisateurNotFoundException::new);
		
		if(userRequest.getNom() != null) {
			utilisateur.setNom(userRequest.getNom());
		}
		
		if(userRequest.getPrenom() != null) {
			utilisateur.setPrenom(userRequest.getPrenom());
		}
		
		if(userRequest.getEmail() != null) {
			utilisateur.setEmail(userRequest.getEmail());
		}
		
		if(userRequest.getMdp() != null) {
			if (userRequest.getMdp().equals(userRequest.getMdpVerif()) == false) {
	            throw new EntityNotValidException();
	        }
			utilisateur.setMdp(this.passwordEncoder.encode(userRequest.getMdp()));
		}
		
		Utilisateur savedUtilisateur = this.repoUtilisateur.save(utilisateur);
	    return ResponseEntity.ok(savedUtilisateur);
	}

	@PutMapping("/reset-mdp/{pseudo}")
	public ResponseEntity<Utilisateur> edit(@PathVariable String pseudo, @Valid @RequestBody ResetMdpRequest resetMdpRequest, BindingResult result) {
	    if (result.hasErrors()) {
	        throw new EntityNotValidException();
	    }

	    if (resetMdpRequest.getMdp().equals(resetMdpRequest.getMdpVerif()) == false) {
            throw new EntityNotValidException();
        }

	    Utilisateur utilisateur = this.repoUtilisateur.findByPseudo(pseudo).orElseThrow(UtilisateurNotFoundException::new);

	    if (resetMdpRequest.getMdp() != null && !resetMdpRequest.getMdp().isEmpty()) {
	    	utilisateur.setMdp(this.passwordEncoder.encode(resetMdpRequest.getMdp()));
	    }

	    BeanUtils.copyProperties(resetMdpRequest, utilisateur, "mdp");

	    Utilisateur savedUtilisateur = this.repoUtilisateur.save(utilisateur);
	    return ResponseEntity.ok(savedUtilisateur);
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
