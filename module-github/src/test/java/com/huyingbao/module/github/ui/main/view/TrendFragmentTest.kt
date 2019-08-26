package com.huyingbao.module.github.ui.main.view

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.huyingbao.core.test.fragment.FragmentScenarioRule
import com.huyingbao.module.github.GithubApplication
import com.huyingbao.module.github.R
import com.huyingbao.module.github.ui.login.view.LoginActivity
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(application = GithubApplication::class, sdk = [28])
class TrendFragmentTest {
    @get:Rule
    var scenarioRule = FragmentScenarioRule(
            LoginActivity::class.java,
            TrendFragment::class.java,
            null,
            null,
            R.id.fl_container)

    @Before
    fun setUp() {
        scenarioRule.scenario.moveToState(Lifecycle.State.CREATED)
    }

    @After
    fun tearDown() {
        scenarioRule.scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun getMainActionCreator() {
        scenarioRule.scenario.onFragment { Assert.assertNotNull(it.mainActionCreator) }
    }

    @Test
    fun getLayoutId() {
        scenarioRule.scenario.onFragment { Assert.assertNotNull(it.getLayoutId()) }
    }
}