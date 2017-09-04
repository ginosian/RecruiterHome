import com.recruiting.domain.Certifications;
import com.recruiting.domain.Authority;
import com.recruiting.repository.UserRepository;
import com.recruiting.service.entity.GenericCrudService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by Martha on 4/23/2017.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@SpringBootTest(classes = Application.class)
//@AutoConfigureBefore(DataSourceAutoConfiguration.class)
//@TestPropertySource(locations = "classpath:/test.properties")
public class TestRepository {

    @Autowired
    GenericCrudService genericCrudService;

    @Autowired
    @Qualifier("userService")
    UserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

//    @Test
    public void testServices() {
        Assert.assertNotNull(genericCrudService);
//        Assert.assertNotNull(userDetailsService);
//        Assert.assertNotNull(userRepository);
    }

//    @Test
    public void testCertificationsCreation() {
        Certifications certifications = (Certifications) genericCrudService.save(new Certifications(DataUtils.nextName()));
        Assert.assertNotNull(certifications.getId());
    }

//    @Test
    public void testAuthorityCreation() {
        Authority authority = (Authority) genericCrudService.save(new Authority("ADMIN"));
        Assert.assertNotNull(authority.getId());
    }

//    @Test
    public void testUser() {
//        Authority authority = (Authority) genericCrudService.save(new Authority("COMPANY"));
//        Assert.assertNotNull(authority.getId());
//
//        List authorityList = new ArrayList();
//        authorityList.add(authority);
//
//        CompanyStaff companyStaff = new CompanyStaff(DataUtils.nextEmail(), "password", "1234");
//        companyStaff  = (CompanyStaff) genericCrudService.save(companyStaff);
//        Assert.assertNotNull(companyStaff.getId());
//
//
//        org.springframework.security.core.userdetails.User userComparable
//                = (org.springframework.security.core.userdetails.User)userDetailsService.loadUserByUsername(companyStaff.getEmail());
//
//        Assert.assertNotNull(userComparable);
//        Assert.assertEquals(companyStaff.getEmail(), userComparable.getUsername());
//        Assert.assertEquals(companyStaff.getPassword(), userComparable.getPassword());
    }


}
