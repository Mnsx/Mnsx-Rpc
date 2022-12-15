package top.mnsx.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tb_good
 */
@TableName(value ="tb_good")
@Data
public class Good implements Serializable {
    /**
     * 订单id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private Long price;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}