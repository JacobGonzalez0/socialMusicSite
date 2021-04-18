package com.music.social.controllers;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import com.music.social.models.AuthGroup;
import com.music.social.models.AuthenticationRequest;
import com.music.social.models.AuthenticationResponse;
import com.music.social.models.User;
import com.music.social.repositories.AuthGroupRepository;
import com.music.social.repositories.UserRepository;
import com.music.social.services.UserServices;
import com.music.social.utils.JwtUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.ResponseEntity;

@RestController
public class AuthenticationController{

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private UserServices userDetailsService;

    @Autowired
    private AuthGroupRepository authGroupRepository;

    @Autowired
    private UserRepository userDao;

	private final BCryptPasswordEncoder bCrypt;

	public AuthenticationController(){
		this.bCrypt = new BCryptPasswordEncoder(11);
	}

	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		try {
			if(this.bCrypt.matches(authenticationRequest.getPassword(),userDetails.getPassword())){
				final String token = jwtTokenUtil.generateToken(userDetails);

				return ResponseEntity.ok(new AuthenticationResponse(token));
			}
			else{
				throw new Exception("Incorrect username or password");
			}
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

	}

    @PostMapping("/register")
    public String register(
        @Valid User user,
        BindingResult bindingResult) throws JSONException {

            if(bindingResult.hasErrors()){
                //create the response, get the first error and return it 
                JSONObject response = new JSONObject();
                response.put("error", bindingResult.getAllErrors().get(0).toString());
                return response.toString();
            }

            BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder(11);

            String hash = bcrypt.encode(user.getPassword());
            
            user.setPassword(hash);
            authGroupRepository.save(new AuthGroup(user.getUsername(),"USER"));
            userDao.save(user);
            
            JSONObject response = new JSONObject();
            response.put("message", "User registered successfully!");
            return response.toString();
    }

}
