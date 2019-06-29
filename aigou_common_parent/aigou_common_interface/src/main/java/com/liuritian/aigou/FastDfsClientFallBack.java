package com.liuritian.aigou;

import com.liuritian.aigou.util.AjaxResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/*@Repository代表仓库. 一般注解在DAO实现类上, 别人看代码时, 就知道这个类是一个跟数据存储有关的类.
@Service代表业务. 一般注解在Service实现类上.
@Controller代表控制器. 一般注解在控制器类上.
如果你的类不是以上类型(数据存储类, 业务类, 控制器), 可以笼统的使用@Component
*/
@Component//泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注
public class FastDfsClientFallBack implements FastDfsClient {


    @Override
    public AjaxResult upload(MultipartFile file) {
        return null;
    }

    @Override
    public AjaxResult delete(String filePath) {
        return null;
    }
}
