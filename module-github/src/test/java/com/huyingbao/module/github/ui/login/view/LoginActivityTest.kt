package com.huyingbao.module.github.ui.login.view

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.huyingbao.module.github.GithubApplication
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

/**
 * Created by liujunfeng on 2019/3/28.
 */
@RunWith(AndroidJUnit4::class)
@Config(application = GithubApplication::class, sdk = [28])
class LoginActivityTest {
    @get:Rule
    var scenarioRule = ActivityScenarioRule(LoginActivity::class.java)

    @get:Rule
    val intentsTestRule = IntentsTestRule(LoginActivity::class.java)

    @Before
    fun setUp() {
        scenarioRule.scenario.moveToState(Lifecycle.State.CREATED)
    }

    @After
    fun tearDown() {
        scenarioRule.scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun getLayoutId() {
        scenarioRule.scenario.onActivity { Assert.assertNotNull(it.getLayoutId()) }
    }

    @Test
    fun toMainActivity() {
        scenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)
        scenarioRule.scenario.onActivity {
        }
    }
}