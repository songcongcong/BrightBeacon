package com.example.retrofitmvplibrary.base;

/**
 * 作者：宋聪聪 on 2019/6/18.
 */

public interface BasePresenter<UiType extends BaseUiInterface> {

    /**
     * 绑定View
     */
    void attachView(UiType view);

    /**
     * 解绑View
     */
    void detachView();
}
