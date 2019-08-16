package com.huyingbao.module.gan;

import android.app.Application;

import com.huyingbao.module.gan.module.GanAppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;

/**
 * Created by liujunfeng on 2019/1/1.
 */
@Singleton
@Component(modules = {GanAppModule.class})
public interface GanComponent extends AndroidInjector<GanApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        GanComponent.Builder application(Application application);

        GanComponent build();
    }
}
