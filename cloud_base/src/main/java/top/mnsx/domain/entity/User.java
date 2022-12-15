package top.mnsx.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName tb_user
 */
@TableName(value ="tb_user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 收件人
     */
    private String username;

    /**
     * 地址
     */
    private String address;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}