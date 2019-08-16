package com.huyingbao.module.gan.ui.random.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.huyingbao.core.arch.model.RxChange;
import com.huyingbao.core.base.flux.activity.BaseFluxFragActivity;
import com.huyingbao.module.common.app.CommonRouter;
import com.huyingbao.module.gan.ui.random.action.RandomAction;
import com.huyingbao.module.gan.ui.random.store.RandomStore;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Route(path = CommonRouter.RandomActivity)
public class RandomActivity extends BaseFluxFragActivity<RandomStore> {
    @Override
    protected Fragment createFragment() {
        return CategoryFragment.newInstance();
    }

    @Override
    public void afterCreate(@Nullable Bundle savedInstanceState) {
    }

    @Subscribe(tags = {RandomAction.TO_SHOW_DATA}, sticky = true)
    public void toShowData(@NonNull RxChange rxChange) {
    }
}
