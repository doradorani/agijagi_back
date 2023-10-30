package com.office.agijagi_back.Controller;

import com.office.agijagi_back.Service.UserService;
import com.office.agijagi_back.Util.Jwt.JwtProvider;
import com.office.agijagi_back.Util.Jwt.TokenService;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = {"1. User"})
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final TokenService tokenService;

    public UserController(UserService userService, JwtProvider jwtProvider, TokenService tokenService) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.tokenService = tokenService;
    }


    @PostMapping("/myPage")
    public String myPage(HttpServletRequest request) {

        //SecurityContextHolder에서 정보 가져옴
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName = userDetails.getUsername();

        System.out.println("현재 사용자의 userName: " + userName);

        return "됐다";
    }

    @PostMapping("/newToken")
    public ResponseEntity newToken(HttpServletRequest request) {

        String refreshToken = "";
        String email = "";
        Cookie[] list = request.getCookies();
        if (list != null) {
            for (Cookie cookie : list) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue().substring(6);
                }
            }
        }

        if(jwtProvider.validateToken(refreshToken)){
            email = userService.getEmailByRefreshToken(refreshToken);
        }

        return tokenService.setNewAccessToken(jwtProvider.newAccessToken(email, "ROLE_USER"));
    }

    @PostMapping("/logOut")
    public void logOut(HttpServletRequest request, HttpServletResponse response){

        String refreshToken = "";
        Cookie[] list = request.getCookies();
        if (list != null) {
            for (Cookie cookie : list) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue().substring(6);
                }
            }
        }
        int deleteToken = userService.logOut(refreshToken);

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

    }

    @PostMapping("/signOut")
    public void signOut(HttpServletRequest request, HttpServletResponse response){

        String refreshToken = "";
        String email = "";
        Cookie[] list = request.getCookies();

        if (list != null) {
            for (Cookie cookie : list) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue().substring(6);
                }
            }
        }

        email = userService.getEmailByRefreshToken(refreshToken);
        int delete = userService.logOut(refreshToken);
        int deleteUser = userService.signOut(email);

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }


}