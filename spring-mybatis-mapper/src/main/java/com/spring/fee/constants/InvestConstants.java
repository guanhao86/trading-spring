package com.spring.fee.constants;

/**
 * 投资变量表
 */
public final class InvestConstants {

    /**
     * 队列
     */
    public static final class Queue{
        private Queue(){}
        //排队中
        public static final String QUEUE_STATUS_ING="1";
        //已购买
        public static final String QUEUE_STATUS_BUY="2";
        //购买失败
        public static final String QUEUE_STATUS_ERR="3";
        //购买失败（解冻金额失败）
        public static final String QUEUE_STATUS_ERR1="4";

        //整仓
        public static final String TYPE_NOAPPEND = "1";
        //续仓
        public static final String TYPE_APPEND = "2";

    }

    /**
     * 计划
     */
    public static final class Plan{
        private Plan(){}
        //主仓
        public static final String PLAN_TYPE_MAIN="1";
        //副仓
        public static final String PLAN_TYPE_SECOND="2";

        //主表
        //执行中
        public static final String MAIN_STATUS_ING="1";
        //已完成
        public static final String MAIN_STATUS_FINISH="2";

        //未续仓
        public static final String MAIN_APPEND_N="0";
        //已续仓
        public static final String MAIN_APPEND_Y="1";

        //分表
        //执行中
        public static final String DETAIL_STATUS_ING="1";
        //已完成
        public static final String DETAIL_STATUS_FINISH="2";

        //未续仓
        public static final String DETAIL_APPEND_N="0";
        //已续仓
        public static final String DETAIL_APPEND_Y="1";

        //结算日志
        public static final String PLAN_LOG_TYPE_SETTLE="1";
        //自动复投日志
        public static final String PLAN_LOG_TYPE_AUTO="2";
    }

    /**
     * 计划
     */
    public static final class Account {
        private Account() {
        }
        //解冻
        public static final String DETAIL_OPERTYPE_UNFREEZE="1";
        //冻结
        public static final String DETAIL_OPERTYPE_FREEZE="2";
        //提现
        public static final String DETAIL_OPERTYPE_WITHDRAW="3";
        //充值
        public static final String DETAIL_OPERTYPE_ADD="4";
        //利息
        public static final String DETAIL_OPERTYPE_INTEREST="5";
        //奖金
        public static final String DETAIL_OPERTYPE_BONUS="6";

        //类型：0、其他',
        public static final String DETAIL_TYPE_OTHER="0";
        //1、理财
        public static final String DETAIL_TYPE_MONEY="1";
        //2、粮仓
        public static final String DETAIL_TYPE_GRANARY="2";

        //子类型 1 购买
        public static final String DETAIL_SUBTYPE_BUY="1";
        //2、排队
        public static final String DETAIL_SUBTYPE_QUEUE="2";

    }

    /**
     * 奖金计算
     */
    public static final class Bonus {
        private Bonus() {
        }
        //类型1、主仓投入金额
        public static final String BONUS_TYPE_MAIN_MONEY="1";
        //2、利息（暂不使用）
        public static final String BONUS_TYPE_INTEREST="2";
        //是否已结算：1已结算
        public static final String BONUS_SETTLE_FLAG_FINISH="1";
        //是否已结算：0待结算
        public static final String BONUS_SETTLE_FLAG="0";

        public static final String TYPE_BONUS="1";

        public static final String TYPE_INTEREST="2";
    }

    /**
     * 替换成配置项数据
     */
    public static final class SysParam{
        private SysParam() {}
        //计划最大数
        public static final String PLAN_MAX_ING = "7";
        //每天是否只能执行一次
        public static final String oneTimeOneDay="1";
        //每天没人只能买一个粮仓计划
        public static final String onePlanOneDay="1";
        //最大购买数量，1:校验计划主表  2:校验主仓数量
        public static final String PLAN_MAX_ING_TYPE="1";
    }

    public static final class MemberConfig {
        private MemberConfig() {}

        //是否自动续仓
        public static final String CONFIG_CODE_AUTO_SECOND = "INVEST_AUTO_SECOND";

        //升级类型：1、注册升级
        public static final String UP_LEVEL_TYPE_REG = "1";

        //升级类型：2、购买升级
        public static final String UP_LEVEL_TYPE_BUY = "2";

        //升级类型：3、推荐
        public static final String UP_LEVEL_TYPE_FORWARD = "3";

        //升级类型：4、业绩大额
        public static final String UP_LEVEL_TYPE_MONEY = "4";
    }

    public static final class MoneyConfig {
        private MoneyConfig() {}

        //数量类型：1、计划总数  2、当前计划数量
        public static final String COUNT_TYPE_ALL = "1";

        //数量类型：1、计划总数  2、当前计划数量
        public static final String COUNT_TYPE_PLAN = "2";
    }
}
