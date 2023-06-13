package fr.projet.api.utilisateur.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ResetMdpRequest {
	
	@NotBlank
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,}$")
	private String mdp;
	
	@NotBlank
	private String mdpVerif;

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getMdpVerif() {
		return mdpVerif;
	}

	public void setMdpVerif(String mdpVerif) {
		this.mdpVerif = mdpVerif;
	}
	
	
}
