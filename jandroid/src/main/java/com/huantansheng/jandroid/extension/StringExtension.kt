package com.huantansheng.jandroid.extension

import com.huantansheng.jandroid.constants.RegexConstants
import java.util.regex.Pattern


/**
 * 检验字符串是否与正则规则相同
 */
fun String.isMatch(regex: String): Boolean {
    return !isNullOrEmpty() && Pattern.matches(regex, this)
}

/**
 * 验证手机号（简单）
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun String.isMobileSimple(): Boolean {
    return isMatch(RegexConstants.REGEX_MOBILE_SIMPLE)
}

/**
 * 验证手机号（精确）
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun String.isMobileExact(): Boolean {
    return isMatch(RegexConstants.REGEX_MOBILE_EXACT)
}

/**
 * 验证电话号码
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun String.isTel(): Boolean {
    return isMatch(RegexConstants.REGEX_TEL)
}

/**
 * 验证身份证号码15位
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun String.isIDCard15(): Boolean {
    return isMatch(RegexConstants.REGEX_ID_CARD15)
}

/**
 * 验证身份证号码18位
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun String.isIDCard18(): Boolean {
    return isMatch(RegexConstants.REGEX_ID_CARD18)
}

/**
 * 验证邮箱
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun String.isEmail(): Boolean {
    return isMatch(RegexConstants.REGEX_EMAIL)
}

/**
 * 验证URL
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun String.isURL(): Boolean {
    return isMatch(RegexConstants.REGEX_URL)
}

/**
 * 验证汉字
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun String.isZh(): Boolean {
    return isMatch(RegexConstants.REGEX_ZH)
}

/**
 * 验证用户名
 *
 * 取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun String.isUsername(): Boolean {
    return isMatch(RegexConstants.REGEX_USERNAME)
}

/**
 * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun String.isDate(): Boolean {
    return isMatch(RegexConstants.REGEX_DATE)
}

/**
 * 验证IP地址
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun String.isIP(): Boolean {
    return isMatch(RegexConstants.REGEX_IP)
}
