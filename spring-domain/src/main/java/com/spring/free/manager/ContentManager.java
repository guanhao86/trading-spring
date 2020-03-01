package com.spring.free.manager;/**
 * Created by hengpu on 2019/2/25.
 */

import com.spring.free.base.BaseManager;
import com.spring.free.domain.Content;
import com.spring.free.mapper.ContentMapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName ContentManager
 * @Description //TODO
 * @Author hengpu
 * @Date 2019/2/25 14:30
 * @Version 1.0
 **/
@Repository
public class ContentManager extends BaseManager<ContentMapper, Content> {
}
