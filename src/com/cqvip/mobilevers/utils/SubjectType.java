package com.cqvip.mobilevers.utils;

public class SubjectType {

	public enum ShowStyle
    {
        /// <summary>
        /// 单选样式(1)
        /// </summary>
       ////[EnumDescription("单选样式", 1)]
        SSS_SINGLE_SEL,
        /// <summary>
        /// 多选样式(2)
        /// </summary>
     //  //[EnumDescription("多选样式", 2)]
        SSS_MULTI_SEL,
        /// <summary>
        /// 填空样式(11)
        /// </summary>
      // //[EnumDescription("填空样式", 3)]
        SSS_FILL,
        /// <summary>
        /// 判断样式(5)
        /// </summary>
     //  //[EnumDescription("判断样式", 4)]
        SSS_JUDGEMENT,
        /// <summary>
        /// 问答样式(4)
        /// </summary>
      // //[EnumDescription("问题样式", 5)]
        SSS_TEXT_QUSTION,
        /// <summary>
        /// 简单单选样式(18)
        /// </summary>
      // //[EnumDescription("简单单选", 6)]
        SSS_SIMPLE_SINGLE_SEL,
        /// <summary>
        /// 简单多选样式(19)
        /// </summary>
      // //[EnumDescription("简单多选", 7)]
        SSS_SIMPLE_MULTI_SEL,
        /// <summary>
        /// 不定项选择样式(3)
        /// </summary>
     //  //[EnumDescription("不定项选择", 8)]
        SSS_SINGLE_MULTI_SEL,
        /// <summary>
        /// 简单不定选样式(20)
        /// </summary>
     //  //[EnumDescription("简单不定多选", 9)]
        SSS_SIMPLE_SINGLE_MULTI_SEL,
        /// <summary>
        /// 简选且有多个正确答案样式(21)
        /// </summary>
      // //[EnumDescription("单选多正确项样式", 10)]
        SSS_SINGLE_SEL_MULTIRIGHTITEM,
        /// <summary>
        /// 单选集样式(6)
        /// </summary>
       //[EnumDescription("单选集样式", 11)]
        SSS_SINGLE_SEL_COLL,
        /// <summary>
        /// 多选集样式(7)
        /// </summary>
       //[EnumDescription("多选集样式", 12)]
        SSS_MULTI_SEL_COLL,
        /// <summary>
        /// 填空集样式(13)
        /// </summary>
       //[EnumDescription("填空集样式", 13)]
        SSS_FILL_COL,
        /// <summary>
        /// 判断集样式(10)
        /// </summary>
       //[EnumDescription("判断集样式", 14)]
        SSS_JUDGEMENT_COLL,
        /// <summary>
        /// 问答集样式(9)
        /// </summary>
       //[EnumDescription("问题集样式", 15)]
        SSS_TEXT_QUSTION_COLL,
        /// <summary>
        /// 相同项单选集样式(14)
        /// </summary>
       //[EnumDescription("相同项单选集", 16)]
        SSS_SAMEITEMS_SINGLE_SEL_COLL,
        /// <summary>
        /// 相同项多选集样式(15)
        /// </summary>
       //[EnumDescription("相同项多选集", 17)]
        SSS_SAMEITEMS_MULTI_SEL_COLL,
        /// <summary>
        /// 不定项选择集(8)
        /// </summary>
       //[EnumDescription("不定项选择集", 18)]
        SSS_SINGLE_MULTI_SEL_COLL,
        /// <summary>
        /// 相同项不定项集(19)
        /// </summary>
       //[EnumDescription("相同项不定项集", 19)]
        SSS_SAMEITEMS_SINGLE_MULTI_SEL_COLL,
        /// <summary>
        /// 任意集合样式(13)
        /// </summary>
       //[EnumDescription("任意集样式", 20)]
        SSS_ANY_COL  ,
        SSS_END
    }
}
