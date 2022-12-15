package top.mnsx.service;

import top.mnsx.domain.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Mnsx_x
* @description 针对表【tb_user】的数据库操作Service
* @createDate 2022-12-15 18:55:07
*/
public interface UserService extends IService<User> {
    User getInfoById(Long userId);
}
