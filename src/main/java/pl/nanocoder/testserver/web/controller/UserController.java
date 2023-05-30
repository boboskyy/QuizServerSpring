package pl.nanocoder.testserver.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;
import pl.nanocoder.testserver.config.CustomUserDetailsService;
import pl.nanocoder.testserver.persistence.model.Login;
import pl.nanocoder.testserver.persistence.model.Person;
import pl.nanocoder.testserver.service.ILoginService;
import pl.nanocoder.testserver.web.dto.ChangePasswordDto;
import pl.nanocoder.testserver.web.error.UserNotFoundException;
import pl.nanocoder.testserver.web.util.GenericResponse;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ILoginService loginService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TokenStore tokenStore;

    //region GET

    @PreAuthorize("hasAuthority('permission.user.get_my_info')")
    @GetMapping("/info")
    public Person info()
    {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = loginService.findByLogin(details.getUsername());
        if(login == null)
            throw new UserNotFoundException();
        return login.getPerson();
    }

    @PreAuthorize("hasAuthority('permission.user.get_permissions')")
    @GetMapping("/permissions")
    public Map<String, Object> permissions()
    {
        Map<String, Object> model = new HashMap<>();


        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = loginService.findByLogin(details.getUsername());
        List<String> permissions = customUserDetailsService.getPermissions(login.getRole());

        model.put("permissions",permissions);
        model.put("role",login.getRole().getName());
        model.put("reset_password",login.isResetPassword());

        return model;
    }

    @PreAuthorize("hasAuthority('permission.user.logout')")
    @GetMapping("/logout")
    public GenericResponse logout(final OAuth2Authentication auth)
    {
        final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth.getDetails();
        String accessToken = details.getTokenValue();
        OAuth2AccessToken token = tokenStore.readAccessToken(accessToken);
        tokenStore.removeAccessToken(token);

        return new GenericResponse("success");
    }

    //endregion

    //region POST

    @PreAuthorize("hasAuthority('permission.user.change_password')")
    @PostMapping("/changepassword")
    public GenericResponse changePassword(@Valid @RequestBody ChangePasswordDto dto) throws RuntimeException
    {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = loginService.findByLogin(details.getUsername());
        if(!login.isResetPassword())
            throw new RuntimeException(""); // user has to be reseted to change password
        login.setPassword(dto.getPassword());
        login.setResetPassword(false);
        loginService.save(login,true);
        return new GenericResponse("success");
    }

    //endregion
}
