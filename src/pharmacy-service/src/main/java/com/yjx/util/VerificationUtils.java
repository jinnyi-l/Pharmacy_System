package com.yjx.util;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class VerificationUtils {

    /**
     * 生成png类型验证码
     *
     * @param response response
     * @throws IOException e
     */
    public String setSpecCaptcha(HttpServletResponse response,
                                 Integer width,
                                 Integer height,
                                 Integer len) throws IOException {
        // png类型 一般设置width:130 height:48
        SpecCaptcha captcha = new SpecCaptcha(width, height, len);
        // 获取验证码的字符
        String text = captcha.text();

        System.out.println("验证码：" + text);
        // 输出验证码
        captcha.out(response.getOutputStream());

        return text;
    }


    /**
     * 生成gif类型验证码
     *
     * @param response response
     * @throws IOException e
     */
    public void setGifCaptcha(HttpServletResponse response,
                              Integer width,
                              Integer height,
                              Integer len) throws IOException {

        // 三个参数分别为宽、高、位数 一般设置width:130 height:48
        GifCaptcha gifCaptcha = new GifCaptcha(width, height, len);
        // 设置类型：字母数字混合
        gifCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        //获取验证码
        String text = gifCaptcha.text();
        System.out.println("验证码为：" + text);
        // 输出验证码
        gifCaptcha.out(response.getOutputStream());
    }


    /**
     * 生成算术类型验证码
     *
     * @param response response
     * @throws IOException e
     */
    public String setArithmeticCaptcha(HttpServletResponse response,
                                       Integer width,
                                       Integer height,
                                       Integer len) throws IOException {
        // 算术类型 一般设置width:130 height:48
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(width, height);
        // 几位数运算，默认是两位
        captcha.setLen(len);
        // 获取运算的公式：4-9+1=?
        captcha.getArithmeticString();
        // 获取运算的结果：-4
        String text = captcha.text();
        System.out.println("计算结果为：" + text);
        // 输出验证码
        captcha.out(response.getOutputStream());

        return text;
    }


    /**
     * 生成中文类型验证码
     *
     * @param response response
     * @throws IOException e
     */
    public void setChineseCaptcha(HttpServletResponse response,
                                  Integer width,
                                  Integer height,
                                  Integer len) throws IOException {

        // 中文类型 一般设置width:130 height:48
        ChineseCaptcha captcha = new ChineseCaptcha(width, height,len);
        //获取验证码
        String text = captcha.text();
        System.out.println("验证码为：" + text);
        // 输出验证码
        captcha.out(response.getOutputStream());
    }

}
