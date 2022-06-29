package testFile;

import com.nhXJH.web.util.file.FileUploadUtils;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: xjh
 * Date: 2022/2/15
 * Time: 21:32
 **/
public class FileUtilTest {

    @Test
    public void getFullPath() {
        System.out.println(FileUploadUtils.getFilePath("/profile//2022/01/21/640793c5-0472-4b03-aad4-1a1c08912a9d.jpg"));
    }
}
