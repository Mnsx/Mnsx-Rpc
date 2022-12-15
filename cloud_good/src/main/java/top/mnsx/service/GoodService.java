package top.mnsx.service;

import top.mnsx.domain.entity.Good;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Mnsx_x
* @description 针对表【tb_good】的数据库操作Service
* @createDate 2022-12-15 18:46:47
*/
public interface GoodService extends IService<Good> {
    Good getInfoById(Long goodId);
}
