package com.music.social.controllers;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import com.music.social.models.AuthGroup;
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

    @PostMapping("/login")
    public String login(
        User user,
        BindingResult bindingResult) throws JSONException {
		
        final UserDetails userDetails = userDetailsService
            .loadUserByUsername(user.getUsername());
		
		try {
			if(this.bCrypt.matches(user.getPassword(),userDetails.getPassword())){
				final String token = jwtTokenUtil.generateToken(userDetails);

				JSONObject response = new JSONObject();
                response.put("token", token);
                return response.toString();
			}
			else{
                JSONObject response = new JSONObject();
                response.put("error", "Incorrect username or password");
                return response.toString();
			}
		}
		catch (BadCredentialsException e) {
			JSONObject response = new JSONObject();
            response.put("error", "Incorrect username or password");
            return response.toString();
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
