package com.yizutiyu.brightbeacon.mvp.uiinterface;

import com.example.retrofitmvplibrary.base.BaseUiInterface;
import com.yizutiyu.brightbeacon.info.LoginInfo;

import java.util.List;

/**
 * 作者：宋聪聪 on 2019/6/19.
 */

public interface LoginUiInterface extends BaseUiInterface {
    void NormalLoginSucces(LoginInfo loginBean);
}
