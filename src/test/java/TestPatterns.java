import com.recruiting.utils.ValidationPatternUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Martha on 5/8/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TestPatterns {

    private Pattern patternEmail;
    private Pattern patternPassword;
    private Pattern patternPhone;
    private Pattern patternAlphabet;
    private Pattern patternAlphabetSpace;

    @Before
    public void setup(){
        patternEmail = Pattern.compile(ValidationPatternUtils.EMAIL_PATTERN);
        patternPassword = Pattern.compile(ValidationPatternUtils.PASSWORD_PATTERN);
        patternPhone = Pattern.compile(ValidationPatternUtils.USA_PHONE_NUMBER_PATTERN);
        patternAlphabet = Pattern.compile(ValidationPatternUtils.ALPHABET_PATTERN);
        patternAlphabetSpace = Pattern.compile(ValidationPatternUtils.ALPHABET_SPACE_PATTERN);
    }


    @Test
    public void testEmailPattern(){

        String validemail = "a@a.com";

        String invalidEmail1 = "aa.com";
        String invalidEmail2 = "aaa";
        String invalidEmail3 = "a@a";
        String invalidEmail4 = "a@a.";
        String invalidEmail5 = "@a.";
        String invalidEmail6 = "a.com";
        String invalidEmail7 = "@a.com";
        String invalidEmail8 = "a@a.8";
        String invalidEmail9 = "@";

        checkValidity(patternEmail, validemail);

        checkInvalidity(patternEmail, invalidEmail1);
        checkInvalidity(patternEmail, invalidEmail2);
        checkInvalidity(patternEmail, invalidEmail3);
        checkInvalidity(patternEmail, invalidEmail4);
        checkInvalidity(patternEmail, invalidEmail5);
        checkInvalidity(patternEmail, invalidEmail6);
        checkInvalidity(patternEmail, invalidEmail7);
        checkInvalidity(patternEmail, invalidEmail8);
        checkInvalidity(patternEmail, invalidEmail9);
    }

    @Test
    public void testPasswordPattern(){

        String validPassword1 = "aasss7jdsk!";
        String validPassword2 = "!akjjhs7kja";
        String validPassword3 = "7dhk!dhka";
        String validPassword4 = "!7kdajdkkj";
        String validPassword5 = "hdkskjjl!7";
        String validPassword6 = "hdkskjjl7!";
        String validPassword7 = "7!hdkskjjl";
        String validPassword8 = "aasss!jdsk7";

        String invalidPassword1 = "shajs7!";
        String invalidPassword2 = "dhfkjds!hsah";
        String invalidPassword3 = "jshdaksj7akjsh";
        String invalidPassword4 = "sldhkalshd!jsdao7hjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj";
        String invalidPassword5 = "hsdflkdshflds";
        String invalidPassword6 = "8759348593584739";
        String invalidPassword7 = "!$#%$%&**&%$#@#$%%^&";
        String invalidPassword8 = "89776879!@#$%%^";

        checkValidity(patternPassword, validPassword1);
        checkValidity(patternPassword, validPassword2);
        checkValidity(patternPassword, validPassword3);
        checkValidity(patternPassword, validPassword4);
        checkValidity(patternPassword, validPassword5);
        checkValidity(patternPassword, validPassword6);
        checkValidity(patternPassword, validPassword7);
        checkValidity(patternPassword, validPassword8);

        checkInvalidity(patternPassword, invalidPassword1);
        checkInvalidity(patternPassword, invalidPassword2);
        checkInvalidity(patternPassword, invalidPassword3);
        checkInvalidity(patternPassword, invalidPassword4);
        checkInvalidity(patternPassword, invalidPassword5);
        checkInvalidity(patternPassword, invalidPassword6);
        checkInvalidity(patternPassword, invalidPassword7);
        checkInvalidity(patternPassword, invalidPassword8);

    }

    @Test
    public void testPhonePattern(){

        String validPhone1 = "1 800 5551212";
        String validPhone2 = "800 678 1212";
        String validPhone3 = "8005551212";
        String validPhone4 = "18005551212";
        String validPhone5 = "+1800 555 1212 extension65432";
        String validPhone6 = "800 5551212 ext3333";

        String invalidPhone1 = "234-911-5678";
        String invalidPhone2 = "314-159-2653";
        String invalidPhone3 = "123-234-5678";

        checkValidity(patternPhone, validPhone1);
        checkValidity(patternPhone, validPhone2);
        checkValidity(patternPhone, validPhone3);
        checkValidity(patternPhone, validPhone4);
        checkValidity(patternPhone, validPhone5);
        checkValidity(patternPhone, validPhone6);

        checkInvalidity(patternPhone, invalidPhone1);
        checkInvalidity(patternPhone, invalidPhone2);
        checkInvalidity(patternPhone, invalidPhone3);

    }

    @Test
    public void testAlphabetSpacePattern(){

        String validAlphabetSpace1 = "aA";
        String validAlphabetSpace2 = "kdfsdk";
        String validAlphabetSpace3 = "kdsjls sjdnnls skjnd";
        String validAlphabetSpace4 = "HKKKJK";
        String validAlphabetSpace5 = "JLLK NLK KNL";

        String invalidAlphabetSpace1 = "jkds7";
        String invalidAlphabetSpace2 = "009";
        String invalidAlphabetSpace3 = "hggj!";
        String invalidAlphabetSpace4 = "^*^%^$";
        String invalidAlphabetSpace5 = " jdldksjl";

        checkValidity(patternAlphabetSpace, validAlphabetSpace1);
        checkValidity(patternAlphabetSpace, validAlphabetSpace2);
        checkValidity(patternAlphabetSpace, validAlphabetSpace3);
        checkValidity(patternAlphabetSpace, validAlphabetSpace4);
        checkValidity(patternAlphabetSpace, validAlphabetSpace5);

        checkInvalidity(patternAlphabetSpace, invalidAlphabetSpace1);
        checkInvalidity(patternAlphabetSpace, invalidAlphabetSpace2);
        checkInvalidity(patternAlphabetSpace, invalidAlphabetSpace3);
        checkInvalidity(patternAlphabetSpace, invalidAlphabetSpace4);
        checkInvalidity(patternAlphabetSpace, invalidAlphabetSpace5);


    }

    @Test
    public void testAlphabetPattern(){

        String validAlphabet1 = "aA";
        String validAlphabet2 = "kdfsdk";
        String validAlphabet4 = "HKKKJK";

        String invalidAlphabet1 = "jkds7";
        String invalidAlphabet2 = "009";
        String invalidAlphabet3 = "hggj!";
        String invalidAlphabet4 = "^*^%^$";
        String invalidAlphabet5 = " jdsflksjld";

        checkValidity(patternAlphabet, validAlphabet1);
        checkValidity(patternAlphabet, validAlphabet2);
        checkValidity(patternAlphabet, validAlphabet4);

        checkInvalidity(patternAlphabet, invalidAlphabet1);
        checkInvalidity(patternAlphabet, invalidAlphabet2);
        checkInvalidity(patternAlphabet, invalidAlphabet3);
        checkInvalidity(patternAlphabet, invalidAlphabet4);
        checkInvalidity(patternAlphabet, invalidAlphabet5);


    }

    private void checkValidity(Pattern pattern, String input){
        Matcher matcher = pattern.matcher(input);
        Assert.assertEquals(matcher.matches(), true);
        Assert.assertNotEquals(matcher.matches(), false);
    }

    private void checkInvalidity(Pattern pattern, String input){
        Matcher matcher = pattern.matcher(input);
        Assert.assertEquals(matcher.matches(), false);
        Assert.assertNotEquals(matcher.matches(), true);
    }
}
