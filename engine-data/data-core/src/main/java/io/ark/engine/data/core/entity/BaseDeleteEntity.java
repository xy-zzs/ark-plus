package io.ark.engine.data.core.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * @author Noah Zhou
 * @description:
 */
@Data
public class BaseDeleteEntity extends BaseEntity{
    /** 逻辑删除：0=正常 1=已删除 */
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;
}
