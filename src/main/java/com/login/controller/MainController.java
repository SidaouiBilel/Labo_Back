package com.login.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.login.model.AdminEmails;
import com.login.model.Article;
import com.login.model.Commentaire;
import com.login.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.login.dao.*;
import com.login.dto.LoginDto;
import com.login.jwt.*;
import com.login.mail.MailSenderService;
import com.login.dto.UsersDto;
import com.login.enums.Message;

import  com.login.model.Confirmation;
import com.login.model.Post;
import com.login.model.Sujet;
import  com.login.dao.AdminEmailsRepository;
import  com.login.dao.ConfirmationRepository;
import  com.login.dao.UserRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@RestController
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

   
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private MessageRepository messageRespository;
    
    @Autowired
    private CommentaireRepoistory cRepository;
    
    @Autowired
    private UserRepositories userRepositories;
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private SujetRepository pubRepositories;
    
    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private AdminEmailsRepository admin;

    @Autowired
    private ConfirmationRepository confirmationRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private AuthenticationManager customAuthenticationManager;

    @Autowired
    private JwtUtils jwtTokenUtil;
    private UsersDto users;
    
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void index(HttpServletResponse response) throws IOException {
        InputStream in = getClass().getClassLoader().getResourceAsStream("readme.txt");
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setStatus(200);
        response.getOutputStream().write(IOUtils.toByteArray(in));
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody LoginDto registration ,HttpServletRequest request, HttpServletResponse response) {
      
		Authentication authenticatio ;
		String j="";
		
        try {
          authenticatio =  customAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(registration.email, registration.password));
        } catch (AuthenticationException exception) {
            response.setStatus(403);
            return Message.notfound.toString();
        }
        SecurityContextHolder.getContext().setAuthentication(authenticatio); 
        return jwtTokenUtil.generateToken(registration.email);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/isregistered", method = RequestMethod.GET)
    public String isRegistered(@RequestParam(value = "email") String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return Message.notfound.toString();
        }
        if (user.isRegistered()) {
            return "true";
        } else {
            return "false";
        }
    }
    
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/signeup", method = RequestMethod.POST)
    public String signeup(@RequestBody LoginDto registration) {
    	if (registration.email == null) {
            return Message.fill.toString();}
    	else{
    		AdminEmails ad = admin.findByEmail(registration.email);
    		if(ad != null)
    			return Message.forbidden.toString();
    	}
    	AdminEmails a= new AdminEmails(registration.email) ;
    	admin.save(a);
    	User user = new User(registration.email,false); 
    			userRepository.merge(user);
    	return Message.success.toString();
    	
   }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody UsersDto registration) {
        if (registration.email == null) {
            return Message.fill.toString();
        } else {
        	AdminEmails ad = admin.findByEmail(registration.email);
            User user = userRepository.findByEmail(registration.email);
            if (ad == null) {
                return Message.notfound.toString();
            }
             users=new UsersDto(registration.firstname, registration.lastname,registration.status);
            
            Confirmation existingConfirmation = confirmationRepository.getLastByUser(user);
            if (existingConfirmation != null && TimeUnit.DAYS.convert(new Date().getTime() - existingConfirmation.getCreatedDate().getTime(), TimeUnit.MILLISECONDS) < 1) {
                return Message.frequencylimit.toString();
            }
            Confirmation confirmation = new Confirmation();
            confirmation.setActive(true);
            confirmation.setCode(UUID.randomUUID().toString());
            try {
                confirmation.setPasswordCandidate(registration.password);
            } catch (Exception e) {
                return e.getMessage();
            }
            confirmation.setUser(user);
            confirmation.setCreatedDate(new Date());
            confirmation = confirmationRepository.merge(confirmation);
            logger.info("User registration attempt " + user.getEmail());
            //Sending email with confirmation.id and confirmation.code showing a confirmation.passwordCandidate
            try {
                mailSenderService.send(user.getEmail(), confirmation.getPasswordCandidate(), false, "?code=" + confirmation.getCode() + "&id=" + confirmation.getId());
            } catch (Exception e) {
                logger.info("Mail sender " + mailSenderService.javaMailSender + " gave an error for " + user.getEmail() + ", " + e.getMessage());
                confirmation.setActive(false);
                confirmationRepository.merge(confirmation);
                return Message.internalerror.toString();
            }
            
            
          
            return Message.success.toString();
        }
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/confirm",params={"code","id"} ,method = RequestMethod.GET)
    public String confirm(@RequestParam("code") String code, @RequestParam("id") Long id, @RequestParam(value = "password", required = false) String password, HttpServletResponse response) {
        Confirmation confirmation = confirmationRepository.getById(id);
        if (confirmation == null || !confirmation.isActive()) {
            response.setStatus(404);
            return Message.notfound.toString();
        }
        if (!confirmation.getCode().equals(code)) {
            response.setStatus(403);
            return Message.forbidden.toString();
        }
        User user = confirmation.getUser();
        try {
            user.setPassword(confirmation.getPasswordCandidate());
        } catch (Exception e) {}
        user.setRegistered(true);
        user.setModifiedDate(new Date());
        if (password != null) {
            try {
                //TODO: Password has to be hashed
                user.setPassword(password);
            } catch (Exception e) {
                return e.getMessage();
            }
        }
          user.setFirstname(users.firstname);
          user.setLastname(users.lastname);
          user.setStatus(users.status);
        userRepository.merge(user);
        List<Confirmation> confirmationList = confirmationRepository.getByUser(user);
        for (Confirmation eachConfirmation: confirmationList) {
            eachConfirmation.setActive(false);
            confirmationRepository.merge(eachConfirmation);
        }
        logger.info("User registration confirmation " + user.getEmail());
        response.setStatus(200);
      
        return Message.success.toString();
    }

    //BILEL CODE
    @CrossOrigin(origins= "http://localhost:4200")
	@RequestMapping(value="/article",method=RequestMethod.GET)
	public List<Article> getaaarticle(@RequestParam(value="id") Long id){
		return articleRepository.getarticles(id);
	}
    @CrossOrigin(origins= "http://localhost:4200")
	@RequestMapping(value="/articles",method=RequestMethod.GET)
	public List<Article> getarticlez(){
		return articleRepository.getinfo();
	}
    
    @CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/members/students",method=RequestMethod.GET)
	public List<User> getstudents(){
		return userRepositories.getmember("Student");
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/members/professors",method=RequestMethod.GET)
	public List<User> getprofessors(){
		return userRepositories.getmember("Professor");
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/members/researchers",method=RequestMethod.GET)
	public List<User> getresearchers(){
		return userRepositories.getmember("Researcher");
	}
	
	@CrossOrigin(origins= "http://localhost:4200")
	@RequestMapping(value="/info",method=RequestMethod.GET)
	public List<User> getinformations(@RequestParam(value="email") String email){
		return userRepositories.getinformation(email);
	}
	
	@CrossOrigin(origins= "http://localhost:4200")
	@RequestMapping(value="/pub",method=RequestMethod.GET)
	public List<Sujet> getpubs(@RequestParam(value="sujet") String sujet){
		return pubRepositories.getpublications(sujet);
	}
	
	@CrossOrigin(origins= "http://localhost:4200")
	@RequestMapping(value="/post",method=RequestMethod.GET)
	public List<Post> getpost(@RequestParam(value="post") Long id){
		return postRepository.getpost(id);
	}
	
	@CrossOrigin(origins= "http://localhost:4200")
	@RequestMapping(value="/comm",method=RequestMethod.GET)
	public List<Post> getcomm(@RequestParam(value="post") Long id){
		return postRepository.getcomments(id);
	}
	
	@CrossOrigin(origins= "http://localhost:4200")
	@RequestMapping(value="/msg",method=RequestMethod.GET)
	public List<com.login.model.Message> getmesae(@RequestParam(value="email") String email){
		return messageRespository.getmsgs(email);
	}
	
	@CrossOrigin(origins="http://localhost:4200")
	@RequestMapping(value="/comment",method=RequestMethod.POST)
	public String sendcomm(@RequestBody Object C) {
		Integer aInteger = Integer.parseInt((String)(((Map)C).get("membre")));
		Integer bInteger = Integer.parseInt((String)((Map)C).get("post"));
		User user = userRepositories.findOne((Long) aInteger.longValue());
		Post posts = postRepository.findOne((Long)bInteger.longValue());
		Commentaire dummy = new Commentaire();
		dummy.setComm((String)((Map)C).get("comm"));
		dummy.setMembre(user);
		dummy.setPost(posts);
		 cRepository.save(dummy);
		 return "done";
	}
}

