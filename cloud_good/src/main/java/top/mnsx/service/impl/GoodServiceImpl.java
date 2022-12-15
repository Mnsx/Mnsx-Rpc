package top.mnsx.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.mnsx.domain.entity.Good;
import top.mnsx.service.GoodService;
import top.mnsx.mapper.GoodMapper;
import org.springframework.stereotype.Service;

/**
* @author Mnsx_x
* @description 针对表【tb_good】的数据库操作Service实现
* @createDate 2022-12-15 18:46:47
*/
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good>
    implements GoodService{

    @Override
    public Good getInfoById(Long goodId) {
        return getBaseMapper().selectById(goodId);
    }
}




