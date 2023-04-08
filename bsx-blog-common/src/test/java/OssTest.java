import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class OssTest {
    static String accessKey = "1J6jMvHuAB-dVzyFgavUBbEpbS4dIQzy7qKSCC1y";
    static String secretKey = "Tu43QglpIBg4-lGBhA9UlXxJ_iScB2CblHR-uC-n";
    static String bucket = "bsx-jzb-blog01";

    public static void main(String[] args) throws FileNotFoundException {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传

//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        FileInputStream inputStream = new FileInputStream("01.jpg");
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "/bsx-test.jpg";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(inputStream, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
//http://rsqxyotjk.bkt.clouddn.com//bsx-test.jpg
    }
}
