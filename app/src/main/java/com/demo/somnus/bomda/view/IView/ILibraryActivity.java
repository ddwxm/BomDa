package com.demo.somnus.bomda.view.IView;

import com.demo.somnus.bomda.model.bean.Library;

import java.util.List;

/**
 * Created by Somnus on 2018/4/4.
 * 图书馆接口
 */

public interface ILibraryActivity {

    /**
     * 加载图书馆list
     * @param libraries
     */
    void loadLibraryList(List<Library> libraries);

    /**
     * 刷新成功
     */
    void RefreshSuccess();
}
