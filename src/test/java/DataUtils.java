import com.recruiting.domain.Authority;
import com.recruiting.domain.CompanyStaff;
import com.recruiting.service.entity.GenericCrudService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Martha on 3/1/2017.
 */
public class DataUtils {
    private static Integer counter = 0;

    public static final String VALID_USERNAME = "a@a.com";

    public static final String VALID_PASSWORD = "password";

    public static final String COMAPANY = "COMPANY";

    public static final String INVALID = "INVALID";

    static String nextKey(){
        return UUID.randomUUID().toString();
    }

    static String nextName(){
        return "Name" + (++counter);
    }

    static String nextEmail(){
        int temp = ++counter;
        return temp + "@" + temp + ".com";
    }

    public static void initUser(GenericCrudService genericCrudService){
        Authority authority = (Authority) genericCrudService.save(new Authority(COMAPANY));
        List authorityList = new ArrayList();
        authorityList.add(authority);
        CompanyStaff companyStaff = new CompanyStaff(DataUtils.nextName(), "1234", DataUtils.nextEmail());
        genericCrudService.save(companyStaff);
    }
}
