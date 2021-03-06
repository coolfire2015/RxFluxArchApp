package com.huyingbao.module.github.ui.user.view

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.huyingbao.module.github.GithubApplication
import com.huyingbao.module.github.R
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = GithubApplication::class, sdk = [28])
class UserActivityTest {
    @get:Rule
    var scenarioRule = ActivityScenarioRule(UserActivity::class.java)

    @Before
    fun setUp() {
        scenarioRule.scenario.moveToState(Lifecycle.State.CREATED)
    }

    @After
    fun tearDown() {
        scenarioRule.scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun afterCreate() {
        scenarioRule.scenario.moveToState(Lifecycle.State.RESUMED)
        scenarioRule.scenario.onActivity {
            Assert.assertNotNull(it.supportFragmentManager.findFragmentById(R.id.fl_container))
        }
    }
}