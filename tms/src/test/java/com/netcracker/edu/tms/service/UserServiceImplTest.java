package com.netcracker.edu.tms.service;

import com.netcracker.edu.tms.mail.service.mail.MailService;
import com.netcracker.edu.tms.security.payload.JwtAuthenticationResponse;
import com.netcracker.edu.tms.security.payload.LoginRequest;
import com.netcracker.edu.tms.security.token.JwtTokenProvider;
import com.netcracker.edu.tms.user.model.Role;
import com.netcracker.edu.tms.user.model.User;
import com.netcracker.edu.tms.user.model.UserWithPassword;
import com.netcracker.edu.tms.user.repository.RoleRepository;
import com.netcracker.edu.tms.user.repository.UserRepository;
import com.netcracker.edu.tms.user.repository.UserWithPasswordRepository;
import com.netcracker.edu.tms.user.service.UserService;
import com.netcracker.edu.tms.user.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.mockito.Mock;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository = mock(UserRepository.class);

    @Mock
    private UserWithPasswordRepository userWithPasswordRepository = mock(UserWithPasswordRepository.class);

    @Mock
    private RoleRepository roleRepository = mock(RoleRepository.class);

    @Mock
    private AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

    @Mock
    private JwtTokenProvider jwtTokenProvider = mock(JwtTokenProvider.class);

    @Mock
    private Authentication authentication = mock(Authentication.class);

    @Mock
    private MailService mailService = mock(MailService.class);

    private final UserService userService = new UserServiceImpl(
            userRepository,
            userWithPasswordRepository,
            roleRepository,
            authenticationManager,
            jwtTokenProvider,
            mailService);

    @Test
    public void createRoleNonExistent() {
        String stubName = "stubName";
        Role stubRole = new Role(stubName);
        when(roleRepository.findByName(stubName)).thenReturn(null);
        when(roleRepository.save(stubRole)).thenReturn(stubRole);
    }

    @Test
    public void createRoleExistent() {
        String stubName = "stubName";
        Role stubRole = new Role(stubName);
        when(roleRepository.findByName(stubName)).thenReturn(stubRole);
    }

    @Test
    public void login() {
        String stubEmail = "stubEmail";
        String stubPassword = "stubPassword";
        LoginRequest stubLoginRequest = new LoginRequest();
        String jwtStub = "jwtStub";

        when(authenticationManager.authenticate(any()))
                .thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn(jwtStub);

        Assert.assertEquals(new JwtAuthenticationResponse(jwtStub), userService.login(stubLoginRequest));
        Assert.assertEquals(authentication, SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    public void registerNonExistentUser() {
        UserWithPassword stubUWP = new UserWithPassword();
        stubUWP.setId(BigInteger.ONE);
        User stubUser = new User();
        Role stubRole = new Role("stubRole");

        when(userWithPasswordRepository.findByEmail(any())).thenReturn(null);

        when(userWithPasswordRepository.save(stubUWP)).thenReturn(stubUWP);

        when(roleRepository.findByName("stubRole")).thenReturn(stubRole);

        UserWithPassword uwp = userWithPasswordRepository.save(stubUWP);

        when(userRepository.findById(uwp.getId())).thenReturn(Optional.of(stubUser));

        Assert.assertEquals(userRepository.findById(uwp.getId()).get(), userService.register(stubUWP));
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerExistentUser() {
        UserWithPassword stubUWP = new UserWithPassword();
        stubUWP.setId(BigInteger.ONE);
        System.out.println(stubUWP);

        when(userWithPasswordRepository.findByEmail(any())).thenReturn(stubUWP);
        userService.register(stubUWP);
    }

    @Test
    public void getAllUsers() {
        User stubUser1 = new User();
        User stubUser2 = new User();
        User stubUser3 = new User();
        User[] stubArray = {stubUser1, stubUser2, stubUser3};
        List<User> stubList = Arrays.asList(stubArray);
        when(userRepository.findAll()).thenReturn(stubList);

        Assert.assertEquals(stubList, userService.getAllUsers());
    }

    @Test
    public void getUserByEmail() {
        String stubEmail = "stubEmail";
        User stubUser = new User();
        when(userRepository.findByEmail(stubEmail)).thenReturn(stubUser);

        Assert.assertEquals(stubUser, userService.getUserByEmail(stubEmail));
    }

    @Test
    public void existsByEmailExistent() {
        String stubEmail = "stubEmail";
        when(userWithPasswordRepository.existsByEmail(stubEmail)).thenReturn(true);

        Assert.assertTrue(userService.existsByEmail(stubEmail));
    }

    @Test
    public void existsByEmailNonExistent() {
        String stubEmail = "stubEmail";
        when(userWithPasswordRepository.existsByEmail(stubEmail)).thenReturn(false);

        Assert.assertFalse(userService.existsByEmail(stubEmail));
    }

    @Test
    public void getUserWithPasswordById() {
        UserWithPassword stubUWP = new UserWithPassword();
        BigInteger stubId = BigInteger.ONE;
        when(userWithPasswordRepository.findById(stubId)).thenReturn(Optional.of(stubUWP));

        Assert.assertEquals(stubUWP, userWithPasswordRepository.findById(stubId).get());
    }

    @Test
    public void getUserById() {
        User stubUser = new User();
        BigInteger stubId = BigInteger.ONE;
        when(userRepository.findById(stubId)).thenReturn(Optional.of(stubUser));

        Assert.assertEquals(stubUser, userRepository.findById(stubId).get());
    }
}
