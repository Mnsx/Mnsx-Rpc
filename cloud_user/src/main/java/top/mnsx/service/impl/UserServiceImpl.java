package top.mnsx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import top.mnsx.domain.entity.User;
import top.mnsx.service.UserService;
import top.mnsx.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Mnsx_x
* @description 针对表【tb_user】的数据库操作Service实现
* @createDate 2022-12-15 18:55:07
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public User getInfoById(Long userId) {
        return getBaseMapper().selectById(userId);
    }
}




