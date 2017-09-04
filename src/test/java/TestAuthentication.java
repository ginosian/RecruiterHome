import com.recruiting.repository.UserRepository;
import com.recruiting.service.entity.GenericCrudService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

/**
 * Created by Marta on 4/29/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = Application.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@TestPropertySource(locations = "classpath:/test.properties")
public class TestAuthentication {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    GenericCrudService genericCrudService;

    @Autowired
    @Qualifier("userService")
    UserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

    @Resource
    private FilterChainProxy springSecurityFilterChain;

    @Resource
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

//    @Before
//    public void setUp() {
//        DataUtils.initUser(genericCrudService);
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//                .addFilter(springSecurityFilterChain)
//                .build();
//    }
//
//    @Test
//    public void loginWithIncorrectCredentials() throws Exception {
//        MockHttpServletRequest requestBuilder = new MockHttpServletRequest("POST", "/login");
//        requestBuilder.addParameter("username", DataUtils.INVALID);
//        requestBuilder.addParameter("password", DataUtils.INVALID);
//
//        mockMvc.perform(requestBuilder)
//                .andExpect(status().isUnauthorized());
//    }
//
//
//    /**
//     * Test the valid user with valid role
//     * */
//    @Test
//    public void testValidRole()
//    {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new Authority(DataUtils.nextKey(), DataUtils.COMAPANY));
//        Authentication authToken = new RememberMeAuthenticationToken(DataUtils.VALID_USERNAME, DataUtils.VALID_PASSWORD, authorities);
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//    }
//
//    /**
//     * Test the valid user with INVALID role
//     * */
//    @Test (expected = AccessDeniedException.class)
//    public void testInvalidRole()
//    {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new Authority(DataUtils.nextKey(), DataUtils.INVALID));
//        Authentication authToken = new RememberMeAuthenticationToken (DataUtils.VALID_USERNAME, DataUtils.VALID_PASSWORD, authorities);
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//    }
//
//    /**
//     * Test the INVALID user
//     * */
//    @Test (expected = AccessDeniedException.class)
//    public void testInvalidUser()
//    {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new Authority(DataUtils.nextKey(), DataUtils.COMAPANY));
//        Authentication authToken = new RememberMeAuthenticationToken(DataUtils.INVALID, DataUtils.INVALID, authorities);
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//    }
}
