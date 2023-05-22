package it.epicode.eaw.auth.payload;

import java.util.HashSet;
import java.util.Set;

import it.epicode.eaw.auth.entity.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JWTAuthResponse {
	private String username;
    private String accessToken;
    private String tokenType = "Bearer";
    private Set<ERole> roles = new HashSet<>();
   
}
