package fr.projet.api.utilisateur;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.projet.api.utilisateur.request.CommentaireRequest;
import fr.projet.api.utilisateur.response.CommentaireResponse;
import fr.projet.config.jwt.JwtUtil;
import fr.projet.exception.PlaylistNotFoundException;
import fr.projet.exception.utilisateur.CommentaireNotFoundException;
import fr.projet.exception.utilisateur.UtilisateurNotFoundException;
import fr.projet.model.musique.Playlist;
import fr.projet.model.utilisateur.Commentaire;
import fr.projet.model.utilisateur.Utilisateur;
import fr.projet.repo.ICommentaireRepository;
import fr.projet.repo.IPlaylistRepository;
import fr.projet.repo.IUtilisateurRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/commentaire")
public class CommentaireApiController {
	
	@Autowired
	private ICommentaireRepository repoCommentaire;
	
	@Autowired
	private IUtilisateurRepository repoUtilisateur;
	
	@Autowired
	private IPlaylistRepository repoPlaylist;

    //recupérer le pseudo de l'utilisateur connecté
    public String getPseudo(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        String pseudo = null;

        Optional<String> optPseudo = JwtUtil.getUsername(jwtToken);
        if (optPseudo.isPresent()) {
			pseudo = optPseudo.get();
        }
        return pseudo;
    }
    
    @GetMapping
	public List<CommentaireResponse> findAll() {
		return this.repoCommentaire.findAll()
				.stream()
				.map(CommentaireResponse::convert)
				.toList();
	}
	
	
	@GetMapping("/playlist/{playlistId}")
	public List<CommentaireResponse> findAllByPlaylist(@PathVariable int playlistId) {
		return this.repoCommentaire.findAllByPlaylist(playlistId)
				.stream()
				.map(CommentaireResponse::convert)
				.toList();
		
	}
	
	@GetMapping("/playlist/{playlistId}/contenu/{contenu}")
	@Transactional
	public List<CommentaireResponse> findAllByPlaylistAndContenuContaining(@PathVariable int playlistId, @PathVariable String contenu) {
		return this.repoCommentaire.findAllByPlaylistAndContenuContaining(playlistId, contenu)
				.stream()
				.map(CommentaireResponse::convert)
				.toList();
	}
	
	@GetMapping("/playlist/{playlistId}/pseudo/{pseudo}/contenu/{contenu}")
	@Transactional
	public List<CommentaireResponse> findAllByPlaylistAndUtilisateurAndContenuContaining(@PathVariable int playlistId, @PathVariable String pseudo, @PathVariable String contenu) {
		
		return this.repoCommentaire.findAllByPlaylistAndUtilisateurAndContenuContaining(playlistId, pseudo, contenu)
				.stream()
				.map(CommentaireResponse::convert)
				.toList();
	}
	
	@GetMapping("/playlist/{playlistId}/pseudo/{pseudo}/date/{year}-{month}-{day} {hour}:{minute}")
	@Transactional
	public List<CommentaireResponse> findAllByPlaylistAndUtilisateurAndDate(@PathVariable int playlistId, @PathVariable String pseudo, @PathVariable int year, @PathVariable int month, @PathVariable int day, @PathVariable int hour, @PathVariable int minute) {
		
		return this.repoCommentaire.findAllByPlaylistAndUtilisateurAndDate(playlistId, pseudo, year, month, day, hour, minute)
				.stream()
				.map(CommentaireResponse::convert)
				.toList();
	}
	
	@GetMapping("/playlist/{playlistId}/pseudo/{pseudo}/date/{year}-{month}-{day} {hour}:{minute}/contenu/{contenu}")
	@Transactional
	public List<CommentaireResponse> findAllByPlaylistAndUtilisateurAndDateAndContenuContaining(
			@PathVariable int playlistId, @PathVariable String pseudo, @PathVariable int year, @PathVariable int month, @PathVariable int day, @PathVariable int hour, @PathVariable int minute, @PathVariable String contenu) {
		
		return this.repoCommentaire.findAllByPlaylistAndUtilisateurAndDateAndContenuContaining(playlistId, pseudo, year, month, day, hour, minute, contenu)
				.stream()
				.map(CommentaireResponse::convert)
				.toList();
	}
	
	@GetMapping("/{id}")
	@Transactional
	public CommentaireResponse findById(@PathVariable int id) {
		Commentaire commentaire = this.repoCommentaire.findById(id).orElseThrow(CommentaireNotFoundException::new);
		
		return CommentaireResponse.convert(commentaire);
	}
	
	
	@PostMapping("playlist/{playlistId}/commenter")
	public CommentaireResponse commenter(@PathVariable int playlistId, @RequestBody CommentaireRequest commentaireRequest,
			@RequestHeader("Authorization") String token) {
		
		String pseudo = getPseudo(token);
		Utilisateur utilisateur = this.repoUtilisateur.findByPseudo(pseudo).orElseThrow(UtilisateurNotFoundException::new);
		Playlist playlist = this.repoPlaylist.findById(playlistId).orElseThrow(PlaylistNotFoundException::new);

		Commentaire commentaire = new Commentaire();
		commentaire.setContenu(commentaireRequest.getContenu());
		commentaire.setCurrentDate();
		commentaire.setUtilisateur(utilisateur);
		commentaire.setPlaylist(playlist);
		BeanUtils.copyProperties(commentaireRequest, commentaire);
		
		
		this.repoCommentaire.save(commentaire);

		return CommentaireResponse.convert(commentaire);
	}
	
	
	@PutMapping("/{id}")
	public Commentaire edit(@PathVariable int id, @Valid @RequestBody CommentaireRequest commentaireRequest) {
		
		Commentaire commentaire = this.repoCommentaire.findById(id).orElseThrow(CommentaireNotFoundException::new);
		
		BeanUtils.copyProperties(commentaireRequest, commentaire);
		
		return this.repoCommentaire.save(commentaire);
	}
	
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable int id) {
		this.repoCommentaire.deleteById(id);
	}

}
