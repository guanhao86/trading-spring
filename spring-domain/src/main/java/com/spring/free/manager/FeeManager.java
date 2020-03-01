package com.spring.free.manager;/**
 * Created by hengpu on 2019/2/25.
 */

import com.spring.free.base.BaseManager;
import com.spring.free.domain.Fee;
import com.spring.free.mapper.FeeMapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName FeeManager
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/2/25 14:30
 * @Version 1.0
 **/
@Repository
public class FeeManager extends BaseManager<FeeMapper, Fee> {
}
