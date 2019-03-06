package com.findme.api;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.model.FilterPagePosts;
import com.findme.model.Post;
import com.findme.model.PostInfo;
import com.findme.service.PostService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Log4j
@RestController
public class PostRestController {
    private PostService postService;

    @Autowired
    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(path = "/save-post", method = RequestMethod.POST)
    public ResponseEntity<String> savePost(@ModelAttribute PostInfo postInfo, HttpSession session){
        if(session.getAttribute("loggedUserId")==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        postInfo.setUserPostedId((String) session.getAttribute("loggedUserId"));
        postService.save(postInfo);
        return new ResponseEntity<>( HttpStatus.OK);
    }


    @RequestMapping(path = "/get-filtered-posts", method = RequestMethod.GET)
    public ResponseEntity<?> postCustomer(@RequestParam Long userId, Boolean ownerPosts, Boolean friendsPosts, Long userPostedId, HttpSession session) {
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }

        FilterPagePosts filter = FilterPagePosts.builder().ownerPosts(ownerPosts).friendsPosts(friendsPosts).userPostedId(userPostedId).build();
        return new ResponseEntity<>(postService.getPostsByFilter(userId, filter), HttpStatus.OK);
    }

    @RequestMapping(path = "/delete-post", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePost(@RequestParam String postId, HttpSession session){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        postService.delete(Long.valueOf(postId), Long.valueOf(loggedUserId));
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @RequestMapping(path = "/get-news", method = RequestMethod.GET)
    public ResponseEntity<?> getNewsFeed(HttpSession session,
                                         @RequestParam int maxResult,
                                         @RequestParam int currentListPart){
        String loggedUserId = (String) session.getAttribute("loggedUserId");
        if(loggedUserId==null) {
            log.warn("User is not authorized");
            return new ResponseEntity<>("You are not logged in to see this information.", HttpStatus.FORBIDDEN);
        }
        List<Post> list = postService.getNewsList(Long.valueOf(loggedUserId), maxResult, currentListPart);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
